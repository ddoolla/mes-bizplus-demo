package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventoryListDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryTransaction;
import com.bizplus.mes.domain.inventory.transaction.InventoryTransactionRepository;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;

    @Override
    public InventoryListDto getInventories(InventorySearchDto dto, Pageable pageable) {
        Page<InventoryDto> inventoryPage = inventoryRepository.findInventoriesGroupByItem(dto, pageable);

        return new InventoryListDto(
                inventoryPage.getContent(),
                Pagination.of(inventoryPage));
    }

    @Transactional
    @Override
    public void changeStock(@Valid InventoryChangeDto dto) {
        // 1. 품목 조회 및 잠금 (동시 입력 방지)
        Item item = itemReader.getForUpdate(dto.getItemId());

        // 2. LOT 검증
        Lot lot = validateAndGetLot(item, dto.getLotId());

        // 3. 증감 수량 계산
        BigDecimal signedQuantity = dto.getType().getSignedQuantity(dto.getQuantity());

        // 4. 현재고 조회 및 잠금 (현재 DB 상태 기준)
        Inventory inventory = inventoryRepository
                .findInventoryForUpdate(dto.getItemId(), dto.getLotId())
                .orElse(null);

        // 5. 현재고 생성 또는 변경
        if (inventory == null) {
            if (signedQuantity.signum() < 0) {
                throw new IllegalStateException("재고가 부족합니다.");
            }

            inventoryRepository.save(new Inventory(item, lot, signedQuantity));

        } else {
            inventory.changeQuantity(signedQuantity);
        }

        // 재고 이력 저장
        LocalDateTime transactionAt = dto.getTransactionAt() != null
                ? dto.getTransactionAt()
                : LocalDateTime.now();

        inventoryTransactionRepository.save(new InventoryTransaction(
                item,
                lot,
                dto.getType(),
                dto.getQuantity(),
                transactionAt
        ));
    }

    private Lot validateAndGetLot(Item item, Long lotId) {
        if (item.isLotManaged()) {
            if (lotId == null) {
                throw new IllegalArgumentException("LOT 관리 품목은 LOT가 필수입니다.");
            }

            Lot lot = lotReader.getById(lotId);

            if (!lot.getItem().getId().equals(item.getId())) {
                throw new IllegalArgumentException("해당 품목의 LOT가 아닙니다.");
            }

            return lot;
        }

        return null;
    }
}

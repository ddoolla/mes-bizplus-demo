package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryTransaction;
import com.bizplus.mes.domain.inventory.transaction.InventoryTransactionRepository;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.lot.LotValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class InventoryManager {

    private final InventoryRepository inventoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;
    private final LotValidator lotValidator;

    /**
     * 재고 잠금 조회
     */
    public Inventory getForUpdate(Long itemId, Long lotId) {
        itemReader.getForUpdate(itemId);

        return inventoryRepository.findInventoryForUpdate(itemId, lotId)
                .orElse(null);
    }

    /**
     * 락 얻는 순서 ★★★
     * 1. item - 동일 품목 재고 변경이 동시에 일어나는 것을 방지
     * 2. inventory - 락을 얻으면서 최신 재고를 조회
     */
    @Transactional
    public void changeStock(@Valid InventoryChangeDto dto) {
        // 1. 품목 조회 및 잠금 (동시 입력 방지)
        Item item = itemReader.getForUpdate(dto.getItemId());
        Lot lot = lotReader.getByIdOrNull(dto.getLotId());

        // 2. LOT 검증
        lotValidator.validate(item, lot);

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
                transactionAt,
                dto.getReferenceType(),
                dto.getReferenceId()
        ));
    }
}

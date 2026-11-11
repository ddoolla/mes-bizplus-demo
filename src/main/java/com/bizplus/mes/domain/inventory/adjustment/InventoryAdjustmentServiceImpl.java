package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentCreateDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentListDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentSearchDto;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.lot.LotValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class InventoryAdjustmentServiceImpl implements InventoryAdjustmentService {

    private final InventoryAdjustmentRepository inventoryAdjustmentRepository;

    private final ItemReader itemReader;
    private final LotReader lotReader;

    private final InventoryManager inventoryManager;
    private final LotValidator lotValidator;

    @Override
    public InventoryAdjustmentListDto getAll(InventoryAdjustmentSearchDto dto, Pageable pageable) {
        Page<InventoryAdjustmentDto> page = inventoryAdjustmentRepository.fetchAll(dto, pageable);

        return new InventoryAdjustmentListDto(page.getContent(), Pagination.of(page));
    }

    @Transactional
    @Override
    public void create(InventoryAdjustmentCreateDto dto) {
        Item item = itemReader.getById(dto.getItemId());
        Lot lot = lotReader.getByIdOrNull(dto.getLotId());

        lotValidator.validate(item, lot);

        Long lotId = lot != null ? lot.getId() : null;

        Inventory inventory = inventoryManager.getForUpdate(item.getId(), lotId);

        BigDecimal beforeQuantity = dto.getBeforeQuantity();
        BigDecimal afterQuantity = dto.getAfterQuantity();

        BigDecimal currentQuantity = inventory != null
                ? inventory.getQuantity()
                : BigDecimal.ZERO;

        if (currentQuantity.compareTo(beforeQuantity) != 0) {
            throw new BusinessException("현재고가 변경되었습니다. 재고를 다시 조회한 후 조정해주세요.");
        }

        int comparison = afterQuantity.compareTo(beforeQuantity);

        if (comparison == 0) {
            return;
        }

        BigDecimal quantity = afterQuantity.subtract(beforeQuantity).abs();

        TransactionType type = comparison > 0
                ? TransactionType.ADJUSTMENT_INCREASE
                : TransactionType.ADJUSTMENT_DECREASE;

        InventoryAdjustment newAdjustment = inventoryAdjustmentRepository.save(
                new InventoryAdjustment(
                        item,
                        lot,
                        dto.getDate(),
                        beforeQuantity,
                        afterQuantity,
                        dto.getReason()
                ));

        inventoryManager.changeStock(new InventoryChangeDto(
                item.getId(),
                lotId,
                type,
                quantity,
                LocalDateTime.now(),
                InventoryReferenceType.INVENTORY_ADJUSTMENT,
                newAdjustment.getId()
        ));
    }
}

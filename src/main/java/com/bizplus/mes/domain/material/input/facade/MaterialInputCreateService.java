package com.bizplus.mes.domain.material.input.facade;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.material.input.MaterialInput;
import com.bizplus.mes.domain.material.input.MaterialInputRepository;
import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.conversion.UomConversionService;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MaterialInputCreateService {

    private final MaterialInputRepository materialInputRepository;

    private final UomConversionService uomConversionService;

    private final WorkOrderReader workOrderReader;
    private final ItemReader itemReader;
    private final UomReader uomReader;
    private final LotReader lotReader;

    private final InventoryManager inventoryManager;

    @Transactional
    public void create(MaterialInputCreateDto dto) {
        WorkOrder workOrder = workOrderReader.getById(dto.getWorkOrderId());
        Item item = itemReader.getById(dto.getItemId());
        Uom inputUom = uomReader.getById(dto.getUomId());

        Lot lot = lotReader.getByIdOrNull(dto.getLotId());

        if (lot != null && !Objects.equals(lot.getItem().getId(), item.getId())) {
            throw new IllegalArgumentException("선택한 자재의 LOT가 아닙니다.");
        }

        if (dto.getQuantity() == null || dto.getQuantity().signum() <= 0) {
            throw new IllegalArgumentException("투입 수량은 0보다 커야합니다.");
        }

        // 재고 단위로 환산
        BigDecimal inventoryQuantity = uomConversionService.convert(
                dto.getQuantity(),
                inputUom.getId(),
                item.getUom().getId()
        );

        Inventory inventory = inventoryManager.getForUpdate(item.getId(), dto.getLotId());

        if (inventory == null
                || inventory.getQuantity().compareTo(inventoryQuantity) < 0) {
            throw new BusinessException("투입 가능한 재고가 부족합니다.");
        }

        LocalDateTime inputAt = LocalDateTime.now();

        // 재고 차감
        inventoryManager.changeStock(new InventoryChangeDto(
                dto.getItemId(),
                dto.getLotId(),
                TransactionType.PRODUCTION_OUT,
                inventoryQuantity,
                inputAt,
                InventoryReferenceType.WORK_ORDER,
                workOrder.getId()
        ));

        // 투입 내역 생성
        materialInputRepository.save(new MaterialInput(
                workOrder,
                item,
                inputUom,
                lot,
                dto.getType(),
                dto.getQuantity(),
                inputAt
        ));
    }
}

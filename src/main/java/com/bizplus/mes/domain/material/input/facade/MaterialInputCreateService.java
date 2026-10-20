package com.bizplus.mes.domain.material.input.facade;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.inventory.InventoryService;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.lot.LotReader;
import com.bizplus.mes.domain.material.input.MaterialInput;
import com.bizplus.mes.domain.material.input.MaterialInputRepository;
import com.bizplus.mes.domain.material.input.MaterialInputStatus;
import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MaterialInputCreateService {

    private final MaterialInputRepository materialInputRepository;

    private final InventoryService inventoryService;

    private final WorkOrderReader workOrderReader;
    private final ItemReader itemReader;
    private final UomReader uomReader;
    private final LotReader lotReader;
    private final InventoryReader inventoryReader;

    @Transactional
    public void create(MaterialInputCreateDto dto) {
        WorkOrder workOrder = workOrderReader.getById(dto.getWorkOrderId());
        Item item = itemReader.getById(dto.getItemId());
        Lot lot = lotReader.getById(dto.getLotId());
        Uom uom = uomReader.getById(dto.getUomId());

        Inventory inventory = inventoryReader.getForUpdate(item.getId(), lot.getId());

        boolean hasEnoughStock =
                inventory.getQuantity().compareTo(dto.getQuantity()) >= 0;

        MaterialInputStatus status;

        if (hasEnoughStock) {
            inventoryService.changeStock(new InventoryChangeDto(
                    dto.getItemId(),
                    dto.getLotId(),
                    TransactionType.PRODUCTION_OUT,
                    dto.getQuantity(),
                    LocalDateTime.now()
            ));

            status = MaterialInputStatus.COMPLETED;

        } else {
            status = MaterialInputStatus.PENDING;
        }

        materialInputRepository.save(new MaterialInput(
                workOrder,
                item,
                uom,
                lot,
                dto.getType(),
                status,
                dto.getQuantity(),
                status == MaterialInputStatus.COMPLETED
                        ? LocalDateTime.now()
                        : null
        ));
    }
}

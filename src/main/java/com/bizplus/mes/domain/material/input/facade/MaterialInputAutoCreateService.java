package com.bizplus.mes.domain.material.input.facade;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryManager;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.inventory.dto.InventoryChangeDto;
import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.material.input.MaterialInput;
import com.bizplus.mes.domain.material.input.MaterialInputRepository;
import com.bizplus.mes.domain.material.input.MaterialInputType;
import com.bizplus.mes.domain.process.material.ConsumptionMethod;
import com.bizplus.mes.domain.process.material.ProcessMaterial;
import com.bizplus.mes.domain.process.material.ProcessMaterialReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.conversion.UomConversionService;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputAutoCreateService {

    private final MaterialInputRepository materialInputRepository;

    private final InventoryReader inventoryReader;
    private final WorkOrderReader workOrderReader;
    private final ProcessMaterialReader processMaterialReader;
    private final InventoryManager inventoryManager;

    private final UomConversionService uomConversionService;

    @Transactional
    public void create(Long workOrderId, BigDecimal productionQuantity) {
        WorkOrder workOrder = workOrderReader.getById(workOrderId);
        Long routingProcessId = workOrder.getProductionOrderProcess().getRoutingProcess().getId();
        List<ProcessMaterial> processMaterials = processMaterialReader.getByRoutingProcessId(routingProcessId);

        LocalDateTime inputAt = LocalDateTime.now();

        if (processMaterials.isEmpty()) {
            return;
        }

        for (ProcessMaterial processMaterial : processMaterials) {
            if (processMaterial.getConsumptionMethod() != ConsumptionMethod.AUTO) {
                continue; // todo auto인 자재만 가져오도록 최적화
            }

            Item inputItem = processMaterial.getItem(); // 소모 자재
            Uom inputUom = processMaterial.getUom();

            // 투입 요구 수량
            BigDecimal requiredQuantity = processMaterial.getQuantity()
                    .multiply(productionQuantity);

            // 재고 단위로 환산
            BigDecimal convertedReqQty = uomConversionService.convert(
                    requiredQuantity,
                    inputUom.getId(), // 투입 단위
                    inputItem.getUom().getId() // 재고 단위
            );

            // 재고 조회
            List<Inventory> inventories = inventoryReader.getAllAvailable(processMaterial.getItem().getId());

            BigDecimal remainingQty = convertedReqQty;

            // 재고가 충분하지 않으면 투입 내역 미생성
            BigDecimal totalStock = inventories.stream()
                    .map(Inventory::getQuantity)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            if (totalStock.compareTo(remainingQty) < 0) {
                continue;
            }

            // 재고가 있으면
            for (Inventory inventory : inventories) {
                if (remainingQty.signum() <= 0) {
                    break;
                }

                Lot stockLot = inventory.getLot();
                BigDecimal stockQty = inventory.getQuantity();
                BigDecimal deductQty = stockQty.min(remainingQty);

                // 투입 내역 생성 (투입 단위)
                BigDecimal inputQty = uomConversionService.convert(
                        deductQty,
                        inputItem.getUom().getId(),
                        inputUom.getId()
                );
                materialInputRepository.save(new MaterialInput(
                        workOrder,
                        inputItem,
                        inputUom,
                        stockLot,
                        MaterialInputType.AUTO,
                        inputQty,
                        inputAt
                ));

                // 재고 차감 (재고 단위)
                inventoryManager.changeStock(new InventoryChangeDto(
                        inventory.getItem().getId(),
                        stockLot != null
                                ? stockLot.getId()
                                : null,
                        TransactionType.PRODUCTION_OUT,
                        deductQty,
                        inputAt,
                        InventoryReferenceType.WORK_ORDER,
                        workOrderId
                ));

                remainingQty = remainingQty.subtract(deductQty);
            }
        }
    }
}

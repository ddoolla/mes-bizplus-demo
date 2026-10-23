package com.bizplus.mes.domain.material.input.facade;

import com.bizplus.mes.domain.inventory.Inventory;
import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.inventory.InventoryService;
import com.bizplus.mes.domain.process.material.ProcessMaterial;
import com.bizplus.mes.domain.process.material.ProcessMaterialReader;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MaterialInputAutoCreateService {

    private final InventoryReader inventoryReader;
    private final WorkOrderReader workOrderReader;
    private final ProcessMaterialReader processMaterialReader;

    private final InventoryService inventoryService;

    @Transactional
    public void create(Long workOrderId, BigDecimal inputQuantity) {
        WorkOrder workOrder = workOrderReader.getById(workOrderId);
        Long routingProcessId = workOrder.getProductionOrderProcess().getRoutingProcess().getId();
        List<ProcessMaterial> processMaterials = processMaterialReader.getByRoutingProcessId(routingProcessId);

        if (processMaterials.isEmpty()) {
            return;
        }

        for (ProcessMaterial processMaterial : processMaterials) {
            // 투입 요구 수량 계산
            BigDecimal requiredQuantity = processMaterial.getQuantity()
                    .multiply(inputQuantity);

            List<Inventory> inventories = inventoryReader.getAllAvailable(processMaterial.getItem().getId());

            for (Inventory inventory : inventories) {
                if (requiredQuantity.compareTo(inventory.getQuantity()) > 0) {

                    requiredQuantity = requiredQuantity.subtract(inventory.getQuantity());

//                    inventoryService.changeStock(new InventoryChangeDto(
//
//                    ));

                }
            }
        }

        // 공정 소모자재를 조회해서
        // 아이디별 최근 로트들을
        // 소모 수량 * 투입수량 만큼 뺀다
    }
}

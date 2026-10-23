package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.inventory.InventoryReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.material.input.dto.MaterialConsumptionDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputSummaryDto;
import com.bizplus.mes.domain.process.material.ProcessMaterial;
import com.bizplus.mes.domain.process.material.ProcessMaterialReader;
import com.bizplus.mes.domain.work.order.WorkOrder;
import com.bizplus.mes.domain.work.order.WorkOrderReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaterialInputServiceImpl implements MaterialInputService {

    private final MaterialInputRepository materialInputRepository;

    private final InventoryReader inventoryReader;
    private final WorkOrderReader workOrderReader;
    private final ProcessMaterialReader processMaterialReader;

    @Override
    public List<MaterialInputSummaryDto> getSummaries(Long workOrderId) {
        return materialInputRepository.fetchMaterialInputSummaries(workOrderId);
    }

    @Override
    public List<MaterialInputDto> getAll(Long workOrderId) {
        return materialInputRepository.fetchMaterialInput(workOrderId);
    }

    @Transactional
    @Override
    public List<MaterialConsumptionDto> getAvailableMaterials(Long workOrderId, Long routingProcessId) {
        WorkOrder workOrder = workOrderReader.getById(workOrderId);
        List<ProcessMaterial> processMaterials = processMaterialReader.getByRoutingProcessId(routingProcessId);
        List<MaterialInputSummaryDto> materialInputs = materialInputRepository.fetchMaterialInputSummaries(workOrderId);

        Map<Long, BigDecimal> inputQuantities = materialInputs.stream().collect(Collectors.toMap(
                dto -> dto.getItem().id(),
                MaterialInputSummaryDto::getTotalQuantity));

        return processMaterials.stream().map(processMaterial -> {
                    Item item = processMaterial.getItem();

                    BigDecimal requiredQuantity = processMaterial.getQuantity()
                            .multiply(workOrder.getQuantity());

                    BigDecimal inputQuantity = inputQuantities.getOrDefault(item.getId(), BigDecimal.ZERO);
                    BigDecimal difference = requiredQuantity.subtract(inputQuantity);
                    BigDecimal remainingQuantity = difference.max(BigDecimal.ZERO);
                    BigDecimal excessQuantity = difference.negate().max(BigDecimal.ZERO);

                    return new MaterialConsumptionDto(
                            item.getId(),
                            item.getCode(),
                            item.getName(),
                            item.getSpecification(),
                            item.isLotManaged(),
                            processMaterial.getUom().getId(),
                            processMaterial.getUom().getCode(),
                            processMaterial.getUom().getScale(),
                            requiredQuantity,
                            inputQuantity,
                            remainingQuantity,
                            excessQuantity
                    );
                })
                .toList();
    }
}

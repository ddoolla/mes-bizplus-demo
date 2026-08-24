package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.bom.item.BomItem;
import com.bizplus.mes.domain.bom.item.BomItemReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialBomCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialItemCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialUpdateDto;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.routing.process.RoutingProcessReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.UomValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProcessMaterialServiceImpl implements ProcessMaterialService {

    private final ProcessMaterialRepository processMaterialRepository;

    private final ItemReader itemReader;
    private final UomReader uomReader;
    private final BomItemReader bomItemReader;
    private final RoutingProcessReader routingProcessReader;
    private final ProcessMaterialReader processMaterialReader;

    private final UomValidator uomValidator;

    @Override
    public List<ProcessMaterialDto> getProcessMaterials(Long routingProcessId) {
        return processMaterialRepository.findProcessMaterials(routingProcessId);
    }

    @Transactional
    @Override
    public void createProcessMaterialsByItem(Long routingProcessId, ProcessMaterialItemCreateDto dto) {
        RoutingProcess routingProcess = routingProcessReader.getById(routingProcessId);

        dto.getItemIds().forEach(itemId -> {
            Item item = itemReader.getById(itemId);

            processMaterialRepository.save(new ProcessMaterial(
                    routingProcess,
                    item,
                    item.getUom(),
                    BigDecimal.ZERO,
                    ConsumptionMethod.BACKFLUSH
            ));
        });
    }

    @Transactional
    @Override
    public void createProcessMaterialsByBom(Long routingProcessId, ProcessMaterialBomCreateDto dto) {
        RoutingProcess routingProcess = routingProcessReader.getById(routingProcessId);

        dto.getBomIds().forEach(bomId -> {
            BomItem bomItem = bomItemReader.getById(bomId);

            processMaterialRepository.save(new ProcessMaterial(
                    routingProcess,
                    bomItem.getItem(),
                    bomItem.getUom(),
                    bomItem.getQuantity(),
                    ConsumptionMethod.BACKFLUSH
            ));
        });
    }

    @Transactional
    @Override
    public void updateProcessMaterials(List<ProcessMaterialUpdateDto> dtos) {
        dtos.forEach(dto -> {
            ProcessMaterial processMaterial = processMaterialReader.getById(dto.getId());
            Uom uom = uomReader.getById(dto.getUomId());

            uomValidator.validateQuantity(dto.getQuantity(), uom);

            processMaterial.update(
                    uom,
                    dto.getQuantity(),
                    dto.getConsumptionMethod()
            );
        });
    }

    @Transactional
    @Override
    public void deleteProcessMaterials(List<Long> ids) {
        ids.forEach(processMaterialRepository::deleteById);
    }
}

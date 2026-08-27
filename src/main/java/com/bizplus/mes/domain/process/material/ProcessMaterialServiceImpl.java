package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.bom.item.BomItem;
import com.bizplus.mes.domain.bom.item.BomItemReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.process.material.dto.*;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.routing.process.RoutingProcessReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import com.bizplus.mes.domain.uom.UomValidator;
import com.bizplus.mes.domain.uom.conversion.UomConversionReader;
import com.bizplus.mes.domain.uom.conversion.dto.ConvertibleUomDto;
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
    private final UomConversionReader uomConversionReader;

    private final UomValidator uomValidator;

    @Override
    public List<ProcessMaterialDto> getProcessMaterials(Long routingProcessId) {
        return processMaterialRepository.findProcessMaterials(routingProcessId);
    }

    @Override
    public List<ProcessMaterialEditDto> getProcessMaterialsForEdit(Long routingProcessId) {
        List<ProcessMaterialDto> processMaterials = processMaterialRepository.findProcessMaterials(routingProcessId);

        return processMaterials.stream()
                .map(processMaterial -> {
                    List<ConvertibleUomDto> convertibleUoms = uomConversionReader
                            .getConvertibleUoms(processMaterial.getItem().stockUom().id());

                    return new ProcessMaterialEditDto(
                            processMaterial,
                            convertibleUoms
                    );
                })
                .toList();
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
    public void updateProcessMaterials(ProcessMaterialUpdateDto dto) {
        dto.getProcessMaterials().forEach(params -> {
            ProcessMaterial processMaterial = processMaterialReader.getById(params.id());
            Uom uom = uomReader.getById(params.consumptionUomId());

            uomValidator.validateQuantity(params.quantity(), uom);

            processMaterial.update(
                    uom,
                    params.quantity(),
                    params.consumptionMethod()
            );
        });
    }

    @Transactional
    @Override
    public void deleteProcessMaterials(List<Long> ids) {
        ids.forEach(processMaterialRepository::deleteById);
    }
}

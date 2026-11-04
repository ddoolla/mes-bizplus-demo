package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.common.validator.QuantityValidator;
import com.bizplus.mes.domain.bom.item.BomItem;
import com.bizplus.mes.domain.bom.item.BomItemReader;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.item.ItemReader;
import com.bizplus.mes.domain.process.material.dto.*;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.routing.process.RoutingProcessReader;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
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
                            .getConvertibleUoms(processMaterial.getItem().uom().id());

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

        for (Long itemId : dto.getItemIds()) {
            // 이미 등록된 소모 자재는 패스
            if (processMaterialRepository.existsByRoutingProcessIdAndItemId(routingProcessId, itemId)) {
                continue;
            }

            Item item = itemReader.getById(itemId);

            processMaterialRepository.save(new ProcessMaterial(
                    routingProcess,
                    item,
                    item.getUom(),
                    BigDecimal.ZERO,
                    ConsumptionMethod.AUTO
            ));
        }
    }

    @Transactional
    @Override
    public void createProcessMaterialsByBom(Long routingProcessId, ProcessMaterialBomCreateDto dto) {
        RoutingProcess routingProcess = routingProcessReader.getById(routingProcessId);

        for (Long bomItemId : dto.getBomItemIds()) {
            BomItem bomItem = bomItemReader.getById(bomItemId);
            Item item = bomItem.getItem();

            // 이미 등록된 소모 자재는 패스
            if (processMaterialRepository.existsByRoutingProcessIdAndItemId(routingProcessId, item.getId())) {
                continue;
            }

            processMaterialRepository.save(new ProcessMaterial(
                    routingProcess,
                    item,
                    bomItem.getUom(),
                    bomItem.getQuantity(),
                    ConsumptionMethod.AUTO
            ));
        }
    }

    @Transactional
    @Override
    public void updateAll(List<ProcessMaterialUpdateDto> dtoList) {
        if (dtoList == null || dtoList.isEmpty()) {
            return;
        }
        dtoList.forEach(dto -> {
            ProcessMaterial processMaterial = processMaterialReader.getById(dto.getId());
            Uom uom = uomReader.getById(dto.getUomId());

            QuantityValidator.validateScale(dto.getQuantity(), uom.getScale());

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

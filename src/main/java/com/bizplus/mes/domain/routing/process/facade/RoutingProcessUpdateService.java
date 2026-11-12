package com.bizplus.mes.domain.routing.process.facade;

import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.inspection.spec.InspectionSpecReader;
import com.bizplus.mes.domain.process.material.ProcessMaterialService;
import com.bizplus.mes.domain.routing.process.RoutingProcess;
import com.bizplus.mes.domain.routing.process.RoutingProcessReader;
import com.bizplus.mes.domain.routing.process.dto.RoutingProcessUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoutingProcessUpdateService {

    private final RoutingProcessReader routingProcessReader;
    private final InspectionSpecReader inspectionSpecReader;

    private final ProcessMaterialService processMaterialService;

    @Transactional
    public void update(Long id, RoutingProcessUpdateDto dto) {
        RoutingProcess routingProcess = routingProcessReader.getById(id);
        InspectionSpec inspectionSpec = inspectionSpecReader.getByIdOrNull(dto.getInspectionSpecId());

        routingProcess.update(
                inspectionSpec,
                dto.isProcessInspectionRequired()
        );

        processMaterialService.updateAll(dto.getProcessMaterials());
    }
}

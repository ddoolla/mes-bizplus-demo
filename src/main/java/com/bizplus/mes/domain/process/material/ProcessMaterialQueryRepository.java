package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.process.material.dto.ProcessMaterialDto;

import java.util.List;

public interface ProcessMaterialQueryRepository {

    List<ProcessMaterialDto> findProcessMaterials(Long routingProcessId);
}

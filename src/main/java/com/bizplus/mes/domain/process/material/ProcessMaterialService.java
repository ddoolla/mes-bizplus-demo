package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.process.material.dto.ProcessMaterialBomCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialItemCreateDto;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialUpdateDto;

import java.util.List;

public interface ProcessMaterialService {

    List<ProcessMaterialDto> getProcessMaterials(Long routingProcessId);

    void createProcessMaterialsByItem(Long routingProcessId, ProcessMaterialItemCreateDto dto);

    void createProcessMaterialsByBom(Long routingProcessId, ProcessMaterialBomCreateDto dto);

    void updateProcessMaterials(List<ProcessMaterialUpdateDto> dtos);

    void deleteProcessMaterials(List<Long> ids);
}

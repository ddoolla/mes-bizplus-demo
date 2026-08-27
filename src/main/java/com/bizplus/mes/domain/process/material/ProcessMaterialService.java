package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.process.material.dto.*;

import java.util.List;

public interface ProcessMaterialService {

    List<ProcessMaterialDto> getProcessMaterials(Long routingProcessId);

    List<ProcessMaterialEditDto> getProcessMaterialsForEdit(Long routingProcessId);

    void createProcessMaterialsByItem(Long routingProcessId, ProcessMaterialItemCreateDto dto);

    void createProcessMaterialsByBom(Long routingProcessId, ProcessMaterialBomCreateDto dto);

    void updateProcessMaterials(ProcessMaterialUpdateDto dto);

    void deleteProcessMaterials(List<Long> ids);
}

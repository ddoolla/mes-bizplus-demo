package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialConsumptionDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputCreateDto;

import java.util.List;

public interface MaterialInputService {

    List<MaterialConsumptionDto> getConsumptionMaterials(Long workOrderId, Long routingProcessId);

    void create(Long workOrderId, MaterialInputCreateDto dto);
}

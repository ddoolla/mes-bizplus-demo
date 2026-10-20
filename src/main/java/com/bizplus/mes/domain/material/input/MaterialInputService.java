package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialConsumptionDto;

import java.util.List;

public interface MaterialInputService {

    List<MaterialConsumptionDto> getConsumptionMaterials(Long workOrderId, Long routingProcessId);
}

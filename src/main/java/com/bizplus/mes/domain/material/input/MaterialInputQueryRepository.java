package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialInputSummaryDto;

import java.util.List;

public interface MaterialInputQueryRepository {

    List<MaterialInputSummaryDto> fetchMaterialInputSummaries(Long workOrderId);
}

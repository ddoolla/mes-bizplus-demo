package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialInputDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputProductionLotDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputSummaryDto;

import java.util.List;

public interface MaterialInputQueryRepository {

    List<MaterialInputSummaryDto> fetchMaterialInputSummaries(Long workOrderId);

    List<MaterialInputDto> fetchMaterialInput(Long workOrderId);

    List<MaterialInputDto> fetchAllByLotId(Long lotId);

    List<MaterialInputDto> fetchAllByProductionLotId(Long productionLotId);

    List<MaterialInputProductionLotDto> fetchProductionLots(Long inputLotId);
}

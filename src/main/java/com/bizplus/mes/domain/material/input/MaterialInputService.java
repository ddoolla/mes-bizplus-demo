package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialConsumptionDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputDto;
import com.bizplus.mes.domain.material.input.dto.MaterialInputSummaryDto;

import java.util.List;

public interface MaterialInputService {

    List<MaterialInputSummaryDto> getSummaries(Long workOrderId);

    List<MaterialInputDto> getAll(Long workOrderId);

    /**
     * 투입 대상 자재 목록
     *
     * @param workOrderId      - 작업지시 아이디
     * @param routingProcessId - 제품 공정 아이디
     * @return 투입 대상 자재 목록
     */
    List<MaterialConsumptionDto> getAvailableMaterials(Long workOrderId, Long routingProcessId);
}

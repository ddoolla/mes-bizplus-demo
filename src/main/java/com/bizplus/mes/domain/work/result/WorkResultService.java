package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.result.dto.WorkResultUpdateDto;

public interface WorkResultService {

    void create(Long workOrderId);

    void update(Long workOrderId, WorkResultUpdateDto dto);
}

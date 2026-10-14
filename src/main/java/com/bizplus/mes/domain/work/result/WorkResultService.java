package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.order.dto.WorkStopDto;

public interface WorkResultService {

    void createWorkResult(Long workOrderId);

    void stopWork(Long workOrderId, WorkStopDto dto);
}

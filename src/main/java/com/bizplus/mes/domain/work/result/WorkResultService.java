package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.result.dto.WorkResultListDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultSearchDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultUpdateDto;
import org.springframework.data.domain.Pageable;

public interface WorkResultService {

    WorkResultListDto getAll(WorkResultSearchDto dto, Pageable pageable);

    void create(Long workOrderId);

    void update(Long workOrderId, WorkResultUpdateDto dto);
}

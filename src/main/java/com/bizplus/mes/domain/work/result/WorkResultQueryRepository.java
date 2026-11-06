package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkResultQueryRepository {

    Page<WorkResultDto> fetchAll(WorkResultSearchDto dto, Pageable pageable);

    Optional<WorkResultDto> fetchById(Long id);
}

package com.bizplus.mes.domain.worker;

import com.bizplus.mes.domain.worker.dto.WorkerDto;
import com.bizplus.mes.domain.worker.dto.WorkerSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface WorkerQueryRepository {

    Page<WorkerDto> findWorkers(WorkerSearchDto dto, Pageable pageable);

    Optional<WorkerDto> findWorker(Long id);
}

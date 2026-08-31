package com.bizplus.mes.domain.worker;

import com.bizplus.mes.domain.worker.dto.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkerService {

    WorkerListDto getWorkers(WorkerSearchDto dto, Pageable pageable);

    WorkerDto getWorker(Long id);

    boolean checkWorkerCode(Long id, String code);

    Long createWorker(WorkerCreateDto dto);

    void updateWorker(Long id, WorkerUpdateDto dto);

    void deleteWorkers(List<Long> ids);
}

package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.domain.worker.process.dto.WorkerProcessCreateDto;
import com.bizplus.mes.domain.worker.process.dto.WorkerProcessDto;

import java.util.List;

public interface WorkerProcessService {

    List<WorkerProcessDto> getWorkerProcesses(Long workerId);

    void createWorkerProcesses(Long workerId, WorkerProcessCreateDto dto);

    void deleteWorkerProcesses(List<Long> ids);
}

package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.domain.worker.process.dto.WorkerProcessDto;

import java.util.List;

public interface WorkerProcessQueryRepository {

    List<WorkerProcessDto> findWorkerProcesses(Long workerId);
}

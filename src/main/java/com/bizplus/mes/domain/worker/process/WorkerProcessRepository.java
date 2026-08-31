package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.worker.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WorkerProcessRepository extends JpaRepository<WorkerProcess, Long>, WorkerProcessQueryRepository {

    boolean existsByWorkerAndProcess(Worker worker, Process process);
}

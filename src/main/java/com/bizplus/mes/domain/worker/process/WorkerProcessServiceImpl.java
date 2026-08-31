package com.bizplus.mes.domain.worker.process;

import com.bizplus.mes.domain.process.Process;
import com.bizplus.mes.domain.process.ProcessReader;
import com.bizplus.mes.domain.worker.Worker;
import com.bizplus.mes.domain.worker.WorkerReader;
import com.bizplus.mes.domain.worker.process.dto.WorkerProcessCreateDto;
import com.bizplus.mes.domain.worker.process.dto.WorkerProcessDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerProcessServiceImpl implements WorkerProcessService {

    private final WorkerProcessRepository workerProcessRepository;

    private final ProcessReader processReader;
    private final WorkerReader workerReader;

    @Override
    public List<WorkerProcessDto> getWorkerProcesses(Long workerId) {
        return workerProcessRepository.findWorkerProcesses(workerId);
    }

    @Transactional
    @Override
    public void createWorkerProcesses(Long workerId, WorkerProcessCreateDto dto) {
        Worker worker = workerReader.getById(workerId);

        dto.getProcessIds().forEach(processId -> {
            Process process = processReader.getById(processId);

            if (workerProcessRepository.existsByWorkerAndProcess(worker, process)) {
                return;
            }

            workerProcessRepository.save(new WorkerProcess(worker, process));
        });
    }

    @Transactional
    @Override
    public void deleteWorkerProcesses(List<Long> ids) {
        ids.forEach(workerProcessRepository::deleteById);
    }
}

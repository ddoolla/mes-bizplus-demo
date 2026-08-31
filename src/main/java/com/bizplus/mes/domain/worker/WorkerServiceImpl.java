package com.bizplus.mes.domain.worker;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.user.User;
import com.bizplus.mes.domain.user.UserReader;
import com.bizplus.mes.domain.worker.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;

    private final UserReader userReader;
    private final WorkerReader workerReader;

    @Override
    public WorkerListDto getWorkers(WorkerSearchDto dto, Pageable pageable) {
        Page<WorkerDto> workerPage = workerRepository.findWorkers(dto, pageable);

        return new WorkerListDto(workerPage.getContent(), Pagination.of(workerPage));
    }

    @Override
    public WorkerDto getWorker(Long id) {
        return workerRepository.findWorker(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKER_NOT_FOUND, "id: " + id));
    }

    @Override
    public boolean checkWorkerCode(Long id, String code) {
        boolean exists = workerRepository.existsByCodeAndIdNot(code, id);

        return !exists;
    }

    @Override
    public Long createWorker(WorkerCreateDto dto) {
        User user = userReader.getById(dto.getUserId());

        return workerRepository.save(WorkerMapper.toEntity(user, dto)).getId();
    }

    @Transactional
    @Override
    public void updateWorker(Long id, WorkerUpdateDto dto) {
        Worker worker = workerReader.getById(id);

        WorkerMapper.apply(worker, dto);
    }

    @Transactional
    @Override
    public void deleteWorkers(List<Long> ids) {
        ids.forEach(id -> workerReader.getById(id).delete());
    }
}

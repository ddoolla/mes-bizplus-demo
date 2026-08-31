package com.bizplus.mes.domain.worker;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WorkerReader {

    private final WorkerRepository workerRepository;

    public Worker getById(Long id) {
        return workerRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.WORKER_NOT_FOUND, "id: " + id));
    }
}

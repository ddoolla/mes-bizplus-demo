package com.bizplus.mes.domain.process;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProcessReader {

    private final ProcessRepository processRepository;

    public Process getById(Long id) {
        return processRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.PROCESS_NOT_FOUND, "id: " + id));
    }
}

package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonCodeReader {

    private final CommonCodeRepository commonCodeRepository;

    public CommonCode getOrNull(Long id) {
        return id != null
                ? commonCodeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMON_CODE_NOT_FOUND, "id: " + id))
                : null;
    }

    public CommonCode getById(Long id) {

        return commonCodeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.COMMON_CODE_NOT_FOUND, "id: " + id));
    }
}

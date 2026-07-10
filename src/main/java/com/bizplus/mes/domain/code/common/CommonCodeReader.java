package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.common.exception.ErrorCode;
import com.bizplus.mes.common.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommonCodeReader {

    private final CommonCodeRepository commonCodeRepository;

    public CommonCode getOrNull(Long id) {
        return id != null
                ? commonCodeRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new NotFoundException(ErrorCode.COMMON_CODE_NOT_FOUND, id))
                : null;
    }
}

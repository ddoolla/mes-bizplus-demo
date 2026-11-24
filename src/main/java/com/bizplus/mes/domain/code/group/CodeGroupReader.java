package com.bizplus.mes.domain.code.group;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CodeGroupReader {

    private final CodeGroupRepository codeGroupRepository;

    public CodeGroup getById(Long id) {
        return codeGroupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.CODE_GROUP_NOT_FOUND,
                        "CodeGroup id: %d".formatted(id)
                ));
    }

    public CodeGroup getByKey(CodeGroupKey groupKey) {
        return codeGroupRepository.findByGroupKey(groupKey)
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.CODE_GROUP_NOT_FOUND,
                        "CodeGroupKey: %s".formatted(groupKey)
                ));
    }
}

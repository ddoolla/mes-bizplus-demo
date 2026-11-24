package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.group.CodeGroupKey;

import java.util.List;
import java.util.Optional;

public interface CommonCodeQueryRepository {

    List<CommonCodeDto> findCommonCodes(Long codeGroupId, String code, String name);

    List<CommonCodeDto> findCommonCodes(CodeGroupKey groupKey);

    Optional<CommonCode> findCommonCode(CodeGroupKey groupKey, String name);
}

package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.group.CodeGroupKey;

import java.util.List;

public interface CommonCodeQueryRepository {

    List<CommonCodeDto> findCommonCodes(Long codeGroupId, String code, String name);

    List<CommonCodeDto> findCommonCodes(CodeGroupKey groupKey);
}

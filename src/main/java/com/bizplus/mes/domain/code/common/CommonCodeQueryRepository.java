package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;

import java.util.List;

public interface CommonCodeQueryRepository {

    List<CommonCodeDto> findCommonCodes(Long codeGroupId, String code, String name);
}

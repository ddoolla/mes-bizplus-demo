package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeUpdateDto;

import java.util.List;

public interface CommonCodeService {

    List<CommonCodeDto> getCommonCodes(Long codeGroupId, String code, String name);

    CommonCodeDto getCommonCode(Long id);

    void createCommonCode(Long groupId, CommonCodeCreateDto dto);

    void updateCommonCode(Long id, CommonCodeUpdateDto dto);

    void deleteCommonCodes(List<Long> ids);
}

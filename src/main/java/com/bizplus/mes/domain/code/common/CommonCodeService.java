package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeUpdateDto;
import com.bizplus.mes.domain.code.group.CodeGroupKey;

import java.util.List;

public interface CommonCodeService {

    List<CommonCodeDto> getCommonCodes(Long codeGroupId, String code, String name);

    List<CommonCodeDto> getCommonCodes(CodeGroupKey groupKey);

    CommonCodeDto getCommonCode(Long id);

    /**
     * 삭제된 코드도 중복으로 간주
     */
    boolean checkCode(Long groupId, Long id, String code);

    void createCommonCode(Long groupId, CommonCodeCreateDto dto);

    void updateCommonCode(Long id, CommonCodeUpdateDto dto);

    void deleteCommonCodes(List<Long> ids);
}

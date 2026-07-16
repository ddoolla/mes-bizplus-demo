package com.bizplus.mes.domain.code.common;

import com.bizplus.mes.domain.code.common.dto.CommonCodeCreateDto;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.group.CodeGroup;

public class CommonCodeMapper {

    public static CommonCodeDto toDto(CommonCode commonCode) {

        return new CommonCodeDto(
                commonCode.getId(),
                commonCode.getCode(),
                commonCode.getName(),
                commonCode.getDescription()
        );
    }

    public static CommonCode toEntity(CommonCodeCreateDto dto, CodeGroup codeGroup) {

        return new CommonCode(
                codeGroup,
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }
}

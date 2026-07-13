package com.bizplus.mes.domain.user;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.user.dto.UserCreateDto;

public class UserMapper {

    public static User toEntity(UserCreateDto dto,
                                String encodedPassword,
                                CommonCode departmentCode,
                                CommonCode positionCode) {
        return new User(
                departmentCode,
                positionCode,
                dto.getUserId(),
                encodedPassword,
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getRemark()
        );
    }
}


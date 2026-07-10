package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.RoleCreateDto;

public class RoleMapper {

    public static Role toEntity(RoleCreateDto dto) {
        return new Role(
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }
}

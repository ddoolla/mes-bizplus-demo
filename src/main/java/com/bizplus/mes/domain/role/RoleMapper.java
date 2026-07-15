package com.bizplus.mes.domain.role;

import com.bizplus.mes.domain.role.dto.RoleCreateDto;
import com.bizplus.mes.domain.role.dto.RoleDto;

public class RoleMapper {

    public static RoleDto toDto(Role role) {
        return new RoleDto(
                role.getId(),
                role.getCode(),
                role.getName(),
                role.getDescription()
        );
    }

    public static Role toEntity(RoleCreateDto dto) {
        return new Role(
                dto.getCode(),
                dto.getName(),
                dto.getDescription()
        );
    }
}

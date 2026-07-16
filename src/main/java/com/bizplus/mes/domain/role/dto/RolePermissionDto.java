package com.bizplus.mes.domain.role.dto;

import com.bizplus.mes.domain.permission.dto.MenuPermissionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RolePermissionDto {

    private RoleDto role;
    private List<MenuPermissionDto> menuPermissions;
}

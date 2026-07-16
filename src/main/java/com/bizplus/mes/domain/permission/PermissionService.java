package com.bizplus.mes.domain.permission;

import com.bizplus.mes.domain.permission.dto.MenuPermissionDto;

import java.util.List;

public interface PermissionService {

    List<MenuPermissionDto> getPermissions();
}

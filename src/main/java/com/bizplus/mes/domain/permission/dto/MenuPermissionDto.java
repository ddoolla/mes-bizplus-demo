package com.bizplus.mes.domain.permission.dto;

import com.bizplus.mes.domain.permission.PermissionAction;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class MenuPermissionDto {

    private String menu;
    private Map<PermissionAction, PermissionDto> permissions;
}
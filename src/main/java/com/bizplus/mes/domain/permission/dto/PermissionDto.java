package com.bizplus.mes.domain.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class PermissionDto {

    private final Long id;

    @Setter
    private boolean checked;
}

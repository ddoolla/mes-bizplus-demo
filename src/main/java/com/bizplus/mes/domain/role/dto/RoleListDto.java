package com.bizplus.mes.domain.role.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoleListDto {

    private List<RoleDto> roles;
    private Pagination pagination;
}

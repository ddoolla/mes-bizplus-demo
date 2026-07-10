package com.bizplus.mes.domain.role.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class RoleDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String description;

    @QueryProjection
    public RoleDto(Long id,
                   String code,
                   String name,
                   String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.description = description;
    }
}

package com.bizplus.mes.domain.code.group.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class CodeGroupDto {

    private final Long id;
    private final String name;
    private final String menuName;

    @QueryProjection
    public CodeGroupDto(Long id,
                        String name,
                        String menuName) {
        this.id = id;
        this.name = name;
        this.menuName = menuName;
    }
}

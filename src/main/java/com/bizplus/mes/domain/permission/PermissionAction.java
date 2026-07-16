package com.bizplus.mes.domain.permission;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PermissionAction {

    READ("조회"),
    CREATE("등록"),
    UPDATE("수정"),
    DELETE("삭제");

    private final String description;
}

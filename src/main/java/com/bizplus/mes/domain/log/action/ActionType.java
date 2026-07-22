package com.bizplus.mes.domain.log.action;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * READ - 목록 조회 시 (상세보기 제외 - 너무 많이 쌓임)
 * CREATE - DB 저장 시 (등록 폼 조회 X)
 * UPDATE - DB 수정 시 (수정 폼 조회 X)
 * DELETE - DB 삭제 시 (논리 삭제)
 */
@Getter
@RequiredArgsConstructor
public enum ActionType {

    READ("조회"),
    CREATE("등록"),
    UPDATE("수정"),
    DELETE("삭제");

    private final String description;
}

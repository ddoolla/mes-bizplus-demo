package com.bizplus.mes.domain.log.auth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LogoutType {

    LOGOUT("로그아웃"),
    SESSION_EXPIRED("세션 만료");

    private final String description;
}

package com.bizplus.mes.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    MENU_NOT_FOUND(HttpStatus.NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    COMMON_CODE_NOT_FOUND(HttpStatus.NOT_FOUND, "공통 코드를 찾을 수 없습니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    USER_ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자 역할을 찾을 수 없습니다."),
    PERMISSION_NOT_FOUND(HttpStatus.NOT_FOUND, "권한을 찾을 수 없습니다."),
    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "역할을 찾을 수 없습니다."),

    INVALID_PERMISSION(HttpStatus.BAD_REQUEST, "잘못된 권한 정보입니다."),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "잘못된 요청입니다.");

    private final HttpStatus status;
    private final String message;
}

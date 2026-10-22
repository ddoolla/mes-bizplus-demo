package com.bizplus.mes.common.exception;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;
    private final String detail;

    public BusinessException(ErrorCode errorCode) {
        this(errorCode, errorCode.getMessage(), null);
    }

    public BusinessException(ErrorCode errorCode, String detail) {
        this(errorCode, errorCode.getMessage(), detail);
    }

    public BusinessException(String message) {
        this(ErrorCode.BUSINESS_ERROR, message, null);
    }

    public BusinessException(String message, String detail) {
        this(ErrorCode.BUSINESS_ERROR, message, detail);
    }

    private BusinessException(ErrorCode errorCode, String message, String detail) {
        super(message);
        this.errorCode = errorCode;
        this.detail = detail;
    }
}

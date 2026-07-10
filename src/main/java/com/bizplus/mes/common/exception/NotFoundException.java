package com.bizplus.mes.common.exception;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode errorCode, Object identifier) {
        super(errorCode, " [identifier = " + identifier + "]");
    }
}

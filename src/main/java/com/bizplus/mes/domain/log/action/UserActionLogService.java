package com.bizplus.mes.domain.log.action;

public interface UserActionLogService {

    void success(UserAction userAction,
                 String requestUrl,
                 String ipAddress);

    void fail(UserAction userAction,
              String requestUrl,
              String ipAddress,
              String errorMessage);
}

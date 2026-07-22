package com.bizplus.mes.domain.log.auth;

public interface UserAuthLogService {

    void login(String userId,
               String userName,
               String sessionId,
               String ipAddress);

    void logout(String sessionId, LogoutType logoutType);
}

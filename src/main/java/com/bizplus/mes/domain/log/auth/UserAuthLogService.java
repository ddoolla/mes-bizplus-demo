package com.bizplus.mes.domain.log.auth;

import com.bizplus.mes.domain.log.auth.dto.UserAuthLogListDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogSearchDto;
import org.springframework.data.domain.Pageable;

public interface UserAuthLogService {

    UserAuthLogListDto getUserAuthLogs(UserAuthLogSearchDto dto, Pageable pageable);

    void login(String userId,
               String userName,
               String sessionId,
               String ipAddress);

    void logout(String sessionId, LogoutType logoutType);
}

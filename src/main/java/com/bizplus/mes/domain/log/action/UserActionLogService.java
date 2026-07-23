package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.domain.log.action.dto.UserActionLogListDto;
import com.bizplus.mes.domain.log.action.dto.UserActionLogSearchDto;
import org.springframework.data.domain.Pageable;

public interface UserActionLogService {

    UserActionLogListDto getUserActionLogs(UserActionLogSearchDto dto, Pageable pageable);

    void success(UserAction userAction,
                 String requestUrl,
                 String ipAddress);

    void fail(UserAction userAction,
              String requestUrl,
              String ipAddress,
              String errorMessage);
}

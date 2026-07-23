package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.domain.log.action.dto.UserActionLogDto;
import com.bizplus.mes.domain.log.action.dto.UserActionLogSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserActionLogQueryRepository {

    Page<UserActionLogDto> findUserActionLogs(UserActionLogSearchDto dto, Pageable pageable);
}

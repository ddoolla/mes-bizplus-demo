package com.bizplus.mes.domain.log.auth;

import com.bizplus.mes.domain.log.auth.dto.UserAuthLogDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAuthLogQueryRepository {

    Page<UserAuthLogDto> findUserAuthLogs(UserAuthLogSearchDto dto, Pageable pageable);
}

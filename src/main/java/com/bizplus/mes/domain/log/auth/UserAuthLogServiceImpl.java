package com.bizplus.mes.domain.log.auth;

import com.bizplus.mes.common.pagination.Pagination;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogListDto;
import com.bizplus.mes.domain.log.auth.dto.UserAuthLogSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthLogServiceImpl implements UserAuthLogService {

    private final UserAuthLogRepository userAuthLogRepository;

    @Override
    public UserAuthLogListDto getUserAuthLogs(UserAuthLogSearchDto dto, Pageable pageable) {

        Page<UserAuthLogDto> userAuthLogPage = userAuthLogRepository.findUserAuthLogs(dto, pageable);

        return new UserAuthLogListDto(
                userAuthLogPage.getContent(),
                Pagination.of(userAuthLogPage));
    }

    @Override
    public void login(String userId,
                      String userName,
                      String sessionId,
                      String ipAddress) {

        userAuthLogRepository.save(new UserAuthLog(
                userId,
                userName,
                sessionId,
                ipAddress,
                LocalDateTime.now()
        ));
    }

    /*
    * 로그인 페이지에서도 세션이 발급되기 때문에
    * 로그인 페이지에서 세션이 만료되면 예외가 발생하여 예외처리 X
    * */
    @Transactional
    @Override
    public void logout(String sessionId, LogoutType logoutType) {

        userAuthLogRepository
                .findBySessionIdAndLogoutAtIsNull(sessionId)
                .ifPresent(userAuthLog ->
                        userAuthLog.updateLogout(LocalDateTime.now(), logoutType)
                );


    }
}

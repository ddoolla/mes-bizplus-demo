package com.bizplus.mes.domain.log.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserAuthLogServiceImpl implements UserAuthLogService {

    private final UserAuthLogRepository userAuthLogRepository;

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

    /**
     * 로그인 페이지에서도 세션이 발급되기 때문에
     * 로그인 페이지에서 세션이 만료되면 예외가 발생하여 예외처리 X
     */
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

package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.security.CustomUserDetails;
import com.bizplus.mes.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserActionLogServiceImpl implements UserActionLogService {

    private final UserActionLogRepository userActionLogRepository;

    @Override
    public void success(UserAction userAction,
                        String requestUrl,
                        String ipAddress) {

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        userActionLogRepository.save(new UserActionLog(
                currentUser.getUserId(),
                currentUser.getUserName(),
                userAction.menu(),
                userAction.type(),
                LogResult.SUCCESS,
                requestUrl,
                ipAddress,
                null,
                LocalDateTime.now()
        ));
    }

    @Override
    public void fail(UserAction userAction,
                     String requestUrl,
                     String ipAddress,
                     String errorMessage) {

        CustomUserDetails currentUser = SecurityUtils.getCurrentUser();

        if (errorMessage != null && errorMessage.length() > 500) {

            errorMessage = errorMessage.substring(0, 500);
        }

        userActionLogRepository.save(new UserActionLog(
                currentUser.getUserId(),
                currentUser.getUserName(),
                userAction.menu(),
                userAction.type(),
                LogResult.FAIL,
                requestUrl,
                ipAddress,
                errorMessage,
                LocalDateTime.now()
        ));
    }
}

package com.bizplus.mes.security.handler;

import com.bizplus.mes.domain.log.auth.LogoutType;
import com.bizplus.mes.domain.log.auth.UserAuthLogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.jspecify.annotations.Nullable;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

/**
 * LogoutHandler - 세션 만료 이벤트 전에 실행
 * LogoutSuccessHandler - 세션 만료 이벤트 이후에 실행
 */
@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final UserAuthLogService userAuthLogService;

    public CustomLogoutHandler(@Lazy UserAuthLogService userAuthLogService) {
        this.userAuthLogService = userAuthLogService;
    }

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       @Nullable Authentication authentication) {

        HttpSession session = request.getSession(false);

        if (session != null) {

            userAuthLogService.logout(
                    session.getId(),
                    LogoutType.LOGOUT
            );
        }
    }
}

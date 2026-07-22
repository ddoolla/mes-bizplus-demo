package com.bizplus.mes.security.handler;

import com.bizplus.mes.common.util.IpUtils;
import com.bizplus.mes.domain.log.auth.UserAuthLogService;
import com.bizplus.mes.security.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserAuthLogService userAuthLogService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        HttpSession session = request.getSession();

        userAuthLogService.login(
                user.getUserId(),
                user.getUserName(),
                session.getId(),
                IpUtils.getClientIp(request)
        );

        response.sendRedirect("/");
    }
}

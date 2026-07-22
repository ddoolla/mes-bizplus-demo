package com.bizplus.mes.security.session;

import com.bizplus.mes.domain.log.auth.LogoutType;
import com.bizplus.mes.domain.log.auth.UserAuthLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SessionDestroyedEventListener {

    private final UserAuthLogService userAuthLogService;

    @EventListener
    public void handle(SessionDestroyedEvent event) {

        // 세션 만료로인한 로그아웃 로그 업데이트
        userAuthLogService.logout(event.getId(), LogoutType.SESSION_EXPIRED);
    }
}

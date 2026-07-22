package com.bizplus.mes.domain.log.action;

import com.bizplus.mes.common.util.RequestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class UserActionAspect {

    private final UserActionLogService userActionLogService;

    @Around("@annotation(userAction)")
    public Object logging(ProceedingJoinPoint joinPoint, UserAction userAction) throws Throwable {

        String requestUrl = RequestUtils.getRequestUri();
        String ip = RequestUtils.getClientIp();

        try {

            Object result = joinPoint.proceed();

            try {
                userActionLogService.success(userAction, requestUrl, ip);

            } catch (Exception ex) {
                log.error("사용자 활동 로그 저장 실패", ex);
            }

            return result;

        } catch (Throwable e) {

            try {
                userActionLogService.fail(userAction, requestUrl, ip, e.getMessage());

            } catch (Exception ex) {
                log.error("사용자 활동 로그 저장 실패", ex);
            }

            throw e;
        }
    }
}

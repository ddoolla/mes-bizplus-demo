package com.bizplus.mes.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class RequestUtils {

    public static HttpServletRequest getRequest() {

        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();

        if (attributes instanceof ServletRequestAttributes servletAttributes) {

            return servletAttributes.getRequest();
        }

        return null;
    }

    public static String getRequestUri() {

        HttpServletRequest request = getRequest();

        return request != null
                ? request.getRequestURI()
                : null;
    }

    public static String getClientIp() {

        HttpServletRequest request = getRequest();

        return request != null
                ? IpUtils.getClientIp(request)
                : null;
    }
}

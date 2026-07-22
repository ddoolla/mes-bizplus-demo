package com.bizplus.mes.common.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class IpUtils {

    public static String getClientIp(HttpServletRequest request) {

        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        for (String header : headers) {

            String ip = request.getHeader(header);

            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {

                int index = ip.indexOf(',');

                return index >= 0
                        ? ip.substring(0, index).trim()
                        : ip.trim();
            }
        }

        return request.getRemoteAddr();
    }
}

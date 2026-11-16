package com.bizplus.mes.common.util;

public class AuditUtils {

    /**
     * createdBy 데이터의 "이름 [아이디]" 값 중 이름만 추출
     *
     * @param value - createdBy 데이터
     * @return - 이름
     */
    public static String extractName(String value) {
        if (value == null) {
            return null;
        }

        int index = value.lastIndexOf(" [");

        return index >= 0 ? value.substring(0, index) : value;
    }
}

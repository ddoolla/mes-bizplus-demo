package com.bizplus.mes.common.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public final class CodeGenerator {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    private CodeGenerator() {
    }

    /**
     * 코드 생성 유틸 메서드
     * 코드 포멧: 접두어-날짜(yyMMdd)-숫자세자리
     *
     * @param prefix - 코드 접두어
     * @param date - 날짜
     * @param maxCode - 해당 날짜에 생성된 마지막 코드 (코드 포멧에 맞아야함)
     * @return 새로 생성된 코드 ex) SO-260101-001
     */
    public static String generate(CodePrefix prefix, LocalDate date, String maxCode) {
        String datePart = date.format(DATE_FORMATTER);

        if (maxCode == null) {
            return String.format("%s-%s-001", prefix.getValue(), datePart);
        }

        int lastIndex = maxCode.lastIndexOf("-");

        if (lastIndex == -1) {
            throw new IllegalStateException("코드 형식이 올바르지 않습니다.");
        }

        int nextNumber = Integer.parseInt(
                maxCode.substring(lastIndex + 1)
        ) + 1;

        return String.format("%s-%s-%03d", prefix.getValue(), datePart, nextNumber);
    }
}

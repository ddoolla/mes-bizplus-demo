package com.bizplus.mes.domain.lot;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public enum ExpirationStatus {

    NONE("미설정", "text-secondary"),
    EXPIRED("만료", "text-danger"),
    WITHIN_6_MONTHS("6개월 이내", "text-warning"),
    WITHIN_1_YEAR("1년 이내", "text-info"),
    NORMAL("정상", "text-success");

    private final String description;
    private final String cssClass;

    public static ExpirationStatus from(LocalDate expirationDate) {
        if (expirationDate == null) {
            return NONE;
        }

        LocalDate today = LocalDate.now();

        if (expirationDate.isBefore(today)) {
            return EXPIRED;
        }

        if (!expirationDate.isAfter(today.plusMonths(6))) {
            return WITHIN_6_MONTHS;
        }

        if (!expirationDate.isAfter(today.plusYears(1))) {
            return WITHIN_1_YEAR;
        }

        return NORMAL;
    }
}
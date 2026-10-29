package com.bizplus.mes.domain.item;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public enum ShelfLifeUnit {

    DAY("일"),
    MONTH("개월"),
    YEAR("년");

    private final String description;

    public LocalDate calculateExpirationDate(LocalDate manufacturedDate, int shelfLife) {
        return switch (this) {
            case DAY -> manufacturedDate.plusDays(shelfLife);
            case MONTH -> manufacturedDate.plusMonths(shelfLife);
            case YEAR -> manufacturedDate.plusYears(shelfLife);
        };
    }
}

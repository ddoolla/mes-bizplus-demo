package com.bizplus.mes.common.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class PredicateUtil {

    public static BooleanExpression notDeleted(DateTimePath<LocalDateTime> deletedAt) {
        return deletedAt.isNull();
    }

    public static BooleanExpression contains(StringPath field, String value) {
        return StringUtils.hasText(value)
                ? field.containsIgnoreCase(value)
                : null;
    }

    public static <T> BooleanExpression eq(SimpleExpression<T> field, T value) {
        return value != null
                ? field.eq(value)
                : null;
    }

    public static <T> BooleanExpression in(SimpleExpression<T> field, List<T> values) {
        return values != null && !values.isEmpty()
                ? field.in(values)
                : null;
    }

    public static BooleanExpression startDateGoe(DateTimePath<LocalDateTime> field, LocalDate date) {
        return date != null
                ? field.goe(date.atStartOfDay())
                : null;
    }

    public static BooleanExpression endDateLoe(DateTimePath<LocalDateTime> field, LocalDate date) {
        return date != null
                ? field.loe(date.plusDays(1).atStartOfDay())
                : null;
    }
}

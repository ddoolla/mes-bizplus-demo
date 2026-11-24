package com.bizplus.mes.common.excel;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.function.Function;

// 적용 고민중
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ExcelColumn<T> {

    private final String header;
    private final Function<T, Object> getter;
    private final Function<ExcelStyles, CellStyle> style;

    public static <T> ExcelColumn<T> of(String header, Function<T, Object> getter) {
        return new ExcelColumn<>(header, getter, ExcelStyles::body);
    }

    public static <T> ExcelColumn<T> of(String header,
                                        Function<T, Object> getter,
                                        Function<ExcelStyles, CellStyle> style) {
        return new ExcelColumn<>(header, getter, style);
    }
}

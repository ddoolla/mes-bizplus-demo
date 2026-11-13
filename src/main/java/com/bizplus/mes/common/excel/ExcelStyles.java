package com.bizplus.mes.common.excel;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.Map;

public class ExcelStyles {

    private final Workbook workbook;
    private final Map<Integer, CellStyle> decimalStyles = new HashMap<>();

    private final CellStyle header;
    private final CellStyle body;
    private final CellStyle number;
    private final CellStyle date;

    public ExcelStyles(Workbook workbook) {
        this.workbook = workbook;
        this.header = ExcelUtils.getDefaultHeaderStyle(workbook);
        this.body = ExcelUtils.getDefaultBodyStyle(workbook);
        this.number = ExcelUtils.getDefaultNumberStyle(workbook);
        this.date = ExcelUtils.getDefaultDateStyle(workbook);
    }

    public CellStyle body() {
        return body;
    }

    public CellStyle header() {
        return header;
    }

    public CellStyle number() {
        return number;
    }

    public CellStyle date() {
        return date;
    }

    public CellStyle decimal(int scale) {
        return decimalStyles.computeIfAbsent(
                scale,
                s -> ExcelUtils.getDefaultDecimalStyle(workbook, s)
        );
    }
}

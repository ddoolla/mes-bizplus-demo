package com.bizplus.mes.common.excel;

import org.apache.poi.ss.usermodel.*;

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
        this.body = createBodyStyle();
        this.header = createHeaderStyle();
        this.number = createNumberStyle();
        this.date = createDateStyle();
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
                this::createDecimalStyle
        );
    }

    private CellStyle createBodyStyle() {
        CellStyle cs = workbook.createCellStyle();

        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);

        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);

        return cs;
    }

    private CellStyle createNumberStyle() {
        CellStyle cs = workbook.createCellStyle();
        cs.cloneStyleFrom(body);

        cs.setAlignment(HorizontalAlignment.RIGHT);
        cs.setDataFormat(workbook.createDataFormat().getFormat("#,##0"));

        return cs;
    }

    private CellStyle createDecimalStyle(int scale) {
        CellStyle cs = workbook.createCellStyle();
        cs.cloneStyleFrom(body);
        cs.setAlignment(HorizontalAlignment.RIGHT);

        String format = "#,##0";

        if (scale > 0) {
            format += "." + "0".repeat(scale);
        }

        cs.setDataFormat(workbook.createDataFormat().getFormat(format));

        return cs;
    }

    private CellStyle createDateStyle() {
        CellStyle cs = workbook.createCellStyle();
        cs.cloneStyleFrom(body);

        cs.setDataFormat(workbook.createDataFormat().getFormat("yyyy-mm-dd"));

        return cs;
    }

    private CellStyle createHeaderStyle() {
        CellStyle cs = workbook.createCellStyle();
        cs.cloneStyleFrom(body);

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        cs.setFillForegroundColor(ExcelUtils.getColor(217, 225, 242));
        cs.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cs.setBorderTop(BorderStyle.THIN);
        cs.setBorderRight(BorderStyle.THIN);
        cs.setBorderBottom(BorderStyle.THIN);
        cs.setBorderLeft(BorderStyle.THIN);
        cs.setAlignment(HorizontalAlignment.CENTER);
        cs.setVerticalAlignment(VerticalAlignment.CENTER);
        cs.setFont(headerFont);

        return cs;
    }
}

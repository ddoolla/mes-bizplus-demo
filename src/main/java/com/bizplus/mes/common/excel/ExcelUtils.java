package com.bizplus.mes.common.excel;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelUtils {

    public static String getCellValue(Row row, int columnIndex, DataFormatter formatter) {
        Cell cell = row.getCell(columnIndex);

        return cell == null
                ? ""
                : formatter.formatCellValue(cell).trim();
    }

    public static String[] getRowValues(Row row, int columnCount) {
        DataFormatter formatter = new DataFormatter();
        String[] values = new String[columnCount];

        for (int i = 0; i <columnCount; i++) {
            values[i] = getCellValue(row, i, formatter);
        }

        return values;
    }

    public static Color getColor(int r, int g, int b) {
        return new XSSFColor(new java.awt.Color(r, g, b), new DefaultIndexedColorMap());
    }

    public static boolean isEmptyRow(String[] values) {
        for (String value : values) {
            if (!value.isBlank()) {
                return false;
            }
        }

        return true;
    }

    public static void autoSizeColumns(Sheet sheet, int columnCount) {
        for (int i = 0; i < columnCount; i++) {
            sheet.autoSizeColumn(i);

            int customWidth = sheet.getColumnWidth(i) * 2; // 한글 너비에 맞춤 (약 2배)
            sheet.setColumnWidth(i, Math.min(customWidth, 255 * 256)); // 최대 너비: 255 * 256
        }
    }

    public static void setCell(Row row, int col, Object value, CellStyle cs) {
        Cell cell = row.createCell(col);

        switch (value) {
            case null -> cell.setBlank();
            case String str -> cell.setCellValue(str);
            case LocalDate date -> cell.setCellValue(date);
            case LocalDateTime dateTime -> cell.setCellValue(dateTime);
            case Number number -> cell.setCellValue(number.doubleValue());
            case Boolean bool -> cell.setCellValue(bool);
            default -> cell.setCellValue(value.toString());
        }

        if (cs != null) {
            cell.setCellStyle(cs);
        }
    }

    /**
     * 제목 생성 (최상단에 사용)
     *
     * @param workbook         - XSSFWorkbook
     * @param sheet            - Sheet
     * @param title            - 제목
     * @param cellRangeAddress - 병합 범위 ex) "A1:I1"
     */
    public static void setTitle(Workbook workbook, Sheet sheet, String title, String cellRangeAddress) {
        // 제목 스타일
        CellStyle titleCs = workbook.createCellStyle();
        titleCs.setAlignment(HorizontalAlignment.CENTER);
        titleCs.setVerticalAlignment(VerticalAlignment.CENTER);

        // 제목 폰트
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 24);
        titleCs.setFont(titleFont);

        // 병합 영역
        CellRangeAddress region = CellRangeAddress.valueOf(cellRangeAddress);

        // 제목 행 및 셀
        Row titleRow = sheet.createRow(region.getFirstRow());
        titleRow.setHeightInPoints(50);

        Cell titleCell = titleRow.createCell(region.getFirstColumn());
        titleCell.setCellValue(title);
        titleCell.setCellStyle(titleCs);

        // 셀 병합
        sheet.addMergedRegion(region);
    }

    /**
     * 기간 포함 제목 생성 (최상단에 사용)
     *
     * @param workbook         - XSSFWorkbook
     * @param sheet            - Sheet
     * @param title            - 제목
     * @param startDate        - 시작일
     * @param endDate          - 종료일
     * @param cellRangeAddress - 병합 범위 ex) "A1:I1"
     */
    public static void setTitleWithPeriod(Workbook workbook,
                                          Sheet sheet,
                                          String title,
                                          LocalDate startDate,
                                          LocalDate endDate,
                                          String cellRangeAddress) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 제목 스타일
        CellStyle titleCs = workbook.createCellStyle();
        titleCs.setAlignment(HorizontalAlignment.CENTER);
        titleCs.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCs.setWrapText(true);

        // 제목 폰트
        Font titleFont = workbook.createFont();
        titleFont.setBold(true);
        titleFont.setFontHeightInPoints((short) 24);

        // 기간 폰트
        Font periodFont = workbook.createFont();
        periodFont.setFontHeightInPoints((short) 11);

        // 병합 영역
        CellRangeAddress region = CellRangeAddress.valueOf(cellRangeAddress);

        // 본문
        String period;

        // 제목 + 기간
        if (startDate == null && endDate == null) {
            period = "(기간: 전체)";

        } else {
            String startDateStr = startDate != null
                    ? startDate.format(formatter)
                    : "";

            String endDateStr = endDate != null
                    ? endDate.format(formatter)
                    : "";

            period = "(기간: %s ~ %s)".formatted(startDateStr, endDateStr);
        }

        String text = title + "\n" + period;

        XSSFRichTextString richText = new XSSFRichTextString(text);

        richText.applyFont(0, title.length(), titleFont);
        richText.applyFont(title.length() + 1, text.length(), periodFont);

        // 제목 행 및 셀
        Row titleRow = sheet.createRow(region.getFirstRow());
        titleRow.setHeightInPoints(50);

        Cell titleCell = titleRow.createCell(region.getFirstColumn());
        titleCell.setCellValue(richText);
        titleCell.setCellStyle(titleCs);

        // 셀 병합
        sheet.addMergedRegion(region);
    }

    public static void setHeader(Sheet sheet, int headerRow, List<String> columns, CellStyle style) {
        Row row = sheet.createRow(headerRow);
        int i = 0;

        for (String column : columns) {
            Cell cell = row.createCell(i++);
            cell.setCellValue(column);
            cell.setCellStyle(style);
        }

    }
}

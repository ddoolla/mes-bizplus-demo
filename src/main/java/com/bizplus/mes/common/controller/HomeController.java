package com.bizplus.mes.common.controller;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/excel/test")
    @ResponseBody
    public ResponseEntity<byte[]> testExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        Row row = sheet.createRow(0);

        for (int i = 0; i < 10; i++) {
            Cell cell = row.createCell(i);
            String val = "ㅇ".repeat(i + 1);
            cell.setCellValue(val);

//            sheet.setColumnWidth(i, (val.length() * 2) * 256);
        }

        for (int i = 0; i < 10; i++) {
            sheet.autoSizeColumn(i);
            int width = sheet.getColumnWidth(i);
            sheet.setColumnWidth(i, width * 2);
        }

        try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
            workbook.write(os);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment")
                    .contentType(MediaType.parseMediaType(
                            "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                    ))
                    .contentLength(os.toByteArray().length)
                    .body(os.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

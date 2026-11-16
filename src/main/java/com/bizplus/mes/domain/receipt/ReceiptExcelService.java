package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.excel.ExcelStyles;
import com.bizplus.mes.common.excel.ExcelUtils;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
import com.bizplus.mes.domain.receipt.item.ReceiptItemRepository;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemExcelDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReceiptExcelService {

    private final ReceiptItemRepository receiptItemRepository;

    public byte[] download(ReceiptSearchDto dto) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet();
            ExcelStyles styles = new ExcelStyles(workbook);

            // 제목
            ExcelUtils.setTitleWithPeriod(
                    workbook,
                    sheet,
                    "입고 목록",
                    dto.getStartDate(),
                    dto.getEndDate(),
                    "A1:K1"
            );

            // 헤더
            List<String> headerColumns = List.of(
                    "입고 일자",
                    "거래처",
                    "제품 코드",
                    "제품명",
                    "LOT",
                    "수량",
                    "단위",
                    "단가",
                    "금액",
                    "입고자",
                    "비고"
            );

            ExcelUtils.setHeader(
                    sheet,
                    1,
                    headerColumns,
                    styles.header()
            );

            // 본문
            List<ReceiptItemExcelDto> receiptItems = receiptItemRepository.fetchAllForExcel(dto);

            int rowIndex = 2;

            for (ReceiptItemExcelDto data : receiptItems) {
                Row row = sheet.createRow(rowIndex++);

                int scale = data.getUomScale();

                ExcelUtils.setCell(row, 0, data.getReceiptDate(), styles.date());
                ExcelUtils.setCell(row, 1, data.getPartnerName(), styles.body());
                ExcelUtils.setCell(row, 2, data.getItemCode(), styles.body());
                ExcelUtils.setCell(row, 3, data.getItemName(), styles.body());
                ExcelUtils.setCell(row, 4, data.getLotNo(), styles.body());
                ExcelUtils.setCell(row, 5, data.getQuantity(), styles.decimal(scale));
                ExcelUtils.setCell(row, 6, data.getUomCode(), styles.body());
                ExcelUtils.setCell(row, 7, data.getUnitPrice(), styles.number());
                ExcelUtils.setCell(row, 8, data.getAmount(), styles.number());
                ExcelUtils.setCell(row, 9, data.getCreatedBy(), styles.body());
                ExcelUtils.setCell(row, 10, data.getRemark(), styles.body());
            }

            ExcelUtils.autoSizeColumns(sheet, headerColumns.size());

            workbook.write(out);

            return out.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException("입고 목록 엑셀 생성에 실패했습니다.", e);
        }
    }
}

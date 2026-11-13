package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.excel.ExcelStyles;
import com.bizplus.mes.common.excel.ExcelUtils;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import com.bizplus.mes.domain.shipment.item.ShipmentItemRepository;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDetailDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShipmentExcelService {

    private final ShipmentItemRepository shipmentItemRepository;

    public byte[] downloadExcel(ShipmentSearchDto dto) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Sheet sheet = workbook.createSheet();
            ExcelStyles styles = new ExcelStyles(workbook);

            // 제목
            ExcelUtils.setTitleWithPeriod(
                    workbook,
                    sheet,
                    "출고 목록",
                    dto.getStartDate(),
                    dto.getEndDate(),
                    "A1:I1"
            );

            // 헤더
            List<String> headerColumns = List.of(
                    "출고 일자", "거래처", "제품 코드", "제품명", "수량", " 단가", "금액", "출고자", "비고"
            );

            Row columnRow = sheet.createRow(1);

            for (int i = 0; i < headerColumns.size(); i++) {
                Cell cell = columnRow.createCell(i);
                cell.setCellValue(headerColumns.get(i));
                cell.setCellStyle(styles.header());
            }

            // 본문
            List<ShipmentItemDetailDto> shipmentItems = shipmentItemRepository.fetchAllForExcel(dto);

            int rowIndex = 2;

            for (ShipmentItemDetailDto si : shipmentItems) {
                Row row = sheet.createRow(rowIndex++);

                int scale = si.getItem().uom().scale();

                ExcelUtils.setCell(row, 0, si.getShipment().date(), styles.date());
                ExcelUtils.setCell(row, 1, si.getShipment().partner().name(), styles.body());
                ExcelUtils.setCell(row, 2, si.getItem().code(), styles.body());
                ExcelUtils.setCell(row, 3, si.getItem().name(), styles.body());
                ExcelUtils.setCell(row, 4, si.getQuantity(), styles.decimal(scale));
                ExcelUtils.setCell(row, 5, si.getUnitPrice(), styles.number());
                ExcelUtils.setCell(row, 6, si.getAmount(), styles.number());
                ExcelUtils.setCell(row, 7, si.getShipment().createdBy(), styles.body());
                ExcelUtils.setCell(row, 8, "", styles.body());
            }

            // 열 너비 자동 조정
            for (int i = 0; i < headerColumns.size(); i++) {
                sheet.autoSizeColumn(i);
                int width = sheet.getColumnWidth(i);
                sheet.setColumnWidth(i, Math.max(width, 15 * 256));
            }

            workbook.write(out);

            return out.toByteArray();

        } catch (IOException e) {
            throw new IllegalStateException("출고 목록 엑셀 생성에 실패했습니다.", e);
        }
    }
}

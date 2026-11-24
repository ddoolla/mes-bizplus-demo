package com.bizplus.mes.domain.item;

import com.bizplus.mes.common.excel.ExcelDataValidationUtils;
import com.bizplus.mes.common.excel.ExcelStyles;
import com.bizplus.mes.common.excel.ExcelUtils;
import com.bizplus.mes.domain.code.common.CommonCodeReader;
import com.bizplus.mes.domain.code.common.dto.CommonCodeDto;
import com.bizplus.mes.domain.code.group.CodeGroupKey;
import com.bizplus.mes.domain.item.dto.ItemExcelDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.uom.UomReader;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemExcelService {

    private static final int HEADER_ROW_INDEX = 1;
    private static final int DATA_START_ROW_INDEX = 2;
    private static final int TYPE_COLUMN_INDEX = 2;
    private static final int CATEGORY_COLUMN_INDEX = 3;
    private static final int UOM_COLUMN_INDEX = 5;
    private static final int LOT_MANAGED_COLUMN_INDEX = 7;
    private static final int SHELF_LIFE_UNIT_COLUMN_INDEX = 9;
    private static final int INCOMING_INSPECTION_REQUIRED_COLUMN_INDEX = 10;

    private static final List<String> HEADER_COLUMNS = List.of(
            "코드 *",
            "품목명 *",
            "유형 *",
            "카테고리",
            "규격",
            "단위 *",
            "단가",
            "로트 관리 여부",
            "유효기간",
            "유효기간 단위",
            "입고검사 대상",
            "비고"
    );

    private final ItemRepository itemRepository;

    private final CommonCodeReader commonCodeReader;
    private final UomReader uomReader;

    public byte[] download(ItemSearchDto dto) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XSSFSheet sheet = workbook.createSheet();
            ExcelStyles styles = new ExcelStyles(workbook);

            // 타이틀
            ExcelUtils.setTitle(
                    workbook,
                    sheet,
                    "품목 목록",
                    ExcelUtils.titleRange(HEADER_COLUMNS.size())
            );

            // 헤더 항목
            ExcelUtils.setHeader(
                    sheet,
                    HEADER_ROW_INDEX,
                    HEADER_COLUMNS,
                    styles.header()
            );

            // 품목 유형 드롭다운 적용
            List<String> itemTypes = Arrays.stream(ItemType.values())
                    .map(ItemType::getDescription)
                    .toList();

            ExcelDataValidationUtils.addDropdownList(
                    sheet,
                    DATA_START_ROW_INDEX,
                    TYPE_COLUMN_INDEX,
                    itemTypes
            );

            // 카테고리 드롭다운 적용
            List<String> categories = commonCodeReader.getDtosByGroupKey(CodeGroupKey.ITEM_CATEGORY).stream()
                    .map(CommonCodeDto::getName)
                    .toList();

            if (!categories.isEmpty()) {
                ExcelDataValidationUtils.addDropdownList(
                        sheet,
                        DATA_START_ROW_INDEX,
                        CATEGORY_COLUMN_INDEX,
                        categories
                );
            }

            // 단위 드롭다운 적용
            List<String> uoms = uomReader.getAll().stream()
                    .map(Uom::getCode)
                    .toList();

            if (!uoms.isEmpty()) {
                ExcelDataValidationUtils.addDropdownList(
                        sheet,
                        DATA_START_ROW_INDEX,
                        UOM_COLUMN_INDEX,
                        uoms
                );
            }

            // 로트 관리 여부 드롭다운 적용
            ExcelDataValidationUtils.addDropdownList(
                    sheet,
                    DATA_START_ROW_INDEX,
                    LOT_MANAGED_COLUMN_INDEX,
                    List.of("예", "아니오")
            );

            // 요효기간 단위 드롭다운 적용
            List<String> shelfLifeUnits = Arrays.stream(ShelfLifeUnit.values())
                    .map(ShelfLifeUnit::getDescription)
                    .toList();

            ExcelDataValidationUtils.addDropdownList(
                    sheet,
                    DATA_START_ROW_INDEX,
                    SHELF_LIFE_UNIT_COLUMN_INDEX,
                    shelfLifeUnits
            );

            // 입고검사 대상 드롭다운 적용
            ExcelDataValidationUtils.addDropdownList(
                    sheet,
                    DATA_START_ROW_INDEX,
                    INCOMING_INSPECTION_REQUIRED_COLUMN_INDEX,
                    List.of("예", "아니오")
            );

            // 바디 데이터
            List<ItemExcelDto> items = itemRepository.fetchForExcel(dto);

            int rowIndex = DATA_START_ROW_INDEX;

            for (ItemExcelDto item : items) {
                Row row = sheet.createRow(rowIndex++);
                int col = 0;

                String shelfLifeUnit = item.getShelfLifeUnit() != null
                        ? item.getShelfLifeUnit().getDescription()
                        : "";

                ExcelUtils.setCell(row, col++, item.getCode(), styles.body());
                ExcelUtils.setCell(row, col++, item.getName(), styles.body());
                ExcelUtils.setCell(row, col++, item.getType().getDescription(), styles.body());
                ExcelUtils.setCell(row, col++, item.getCategory(), styles.body());
                ExcelUtils.setCell(row, col++, item.getSpecification(), styles.body());
                ExcelUtils.setCell(row, col++, item.getUomCode(), styles.body());
                ExcelUtils.setCell(row, col++, item.getUnitPrice(), styles.number());
                ExcelUtils.setCell(row, col++, item.isLotManaged() ? "예" : "아니오", styles.body());
                ExcelUtils.setCell(row, col++, item.getShelfLife(), styles.body());
                ExcelUtils.setCell(row, col++, shelfLifeUnit, styles.body());
                ExcelUtils.setCell(row, col++, item.isIncomingInspectionRequired() ? "예" : "아니오", styles.body());
                ExcelUtils.setCell(row, col++, item.getRemark(), styles.body());
            }

            ExcelUtils.autoSizeColumns(sheet, HEADER_COLUMNS.size());

            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException("품목 목록 엑셀 생성 실패", e);
        }
    }
}

package com.bizplus.mes.common.excel;

import org.apache.poi.ss.SpreadsheetVersion;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddressList;

import java.util.List;

public class ExcelValidationUtils {

    public static void addListValidation(Sheet sheet, int startRow, int columnIndex, List<String> values) {
        CellRangeAddressList range = new CellRangeAddressList(
                startRow,
                SpreadsheetVersion.EXCEL2007.getLastRowIndex(),
                columnIndex,
                columnIndex
        );

        DataValidationHelper helper = sheet.getDataValidationHelper();

        DataValidationConstraint constraint = helper.createExplicitListConstraint(values.toArray(String[]::new));

        DataValidation validation = helper.createValidation(constraint, range);

        validation.setSuppressDropDownArrow(true);
        validation.setEmptyCellAllowed(true);
        validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
        validation.setShowErrorBox(true);

        validation.createErrorBox("입력 오류", "목록에 있는 값을 선택해주세요.");

        sheet.addValidationData(validation);
    }
}

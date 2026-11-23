package com.bizplus.mes.common.excel;

import com.bizplus.mes.common.exception.BusinessException;
import org.springframework.web.multipart.MultipartFile;

import java.util.Locale;

public class ExcelValidator {

    public static void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BusinessException("파일이 존재하지 않습니다.");
        }

        String fileName = file.getOriginalFilename();

        if (fileName == null || !fileName.toLowerCase(Locale.ROOT).endsWith(".xlsx")) {
            throw new BusinessException("xlsx 형식의 파일만 업로드할 수 있습니다.");
        }
    }

    public static void validateRequired(String value, String fieldName, int rowIndex) {
        if (value == null || value.isBlank()) {
            throw new BusinessException("%d행: %s은(는) 필수입니다.".formatted(rowIndex + 1, fieldName));
        }
    }
}

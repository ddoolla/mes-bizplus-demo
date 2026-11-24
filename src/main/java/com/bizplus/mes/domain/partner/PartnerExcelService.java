package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.excel.ExcelDataValidationUtils;
import com.bizplus.mes.common.excel.ExcelStyles;
import com.bizplus.mes.common.excel.ExcelUtils;
import com.bizplus.mes.common.excel.ExcelValidator;
import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.formatter.BusinessNumberFormatter;
import com.bizplus.mes.common.formatter.CorporateNumberFormatter;
import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PartnerExcelService {

    private static final int HEADER_ROW_INDEX = 1;

    private static final int DATA_START_ROW_INDEX = 2;

    private static final int TYPE_COLUMN_INDEX = 2;

    private static final List<String> HEADER_COLUMNS = List.of(
            "코드 *",
            "거래처명 *",
            "유형 *",
            "대표자명",
            "사업자등록번호",
            "법인등록번호",
            "전화",
            "팩스",
            "이메일",
            "홈페이지",
            "우편번호",
            "주소",
            "상세주소",
            "비고"
    );


    private final PartnerRepository partnerRepository;

    public byte[] download(PartnerSearchDto dto) {
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XSSFSheet sheet = workbook.createSheet();
            ExcelStyles styles = new ExcelStyles(workbook);

            // 타이틀
            ExcelUtils.setTitle(
                    workbook,
                    sheet,
                    "거래처 목록",
                    ExcelUtils.titleRange(HEADER_COLUMNS.size())
            );

            // 헤더 항목
            ExcelUtils.setHeader(
                    sheet,
                    HEADER_ROW_INDEX,
                    HEADER_COLUMNS,
                    styles.header()
            );

            // 거래처 유형 드롭다운 적용
            List<String> partnerTypes = Arrays.stream(PartnerType.values())
                    .map(PartnerType::getDescription)
                    .toList();

            ExcelDataValidationUtils.addDropdownList(
                    sheet,
                    DATA_START_ROW_INDEX,
                    TYPE_COLUMN_INDEX,
                    partnerTypes
            );

            // 바디 데이터
            List<Partner> partners = partnerRepository.fetchAll(dto);

            int rowIndex = DATA_START_ROW_INDEX;

            for (Partner partner : partners) {
                Row row = sheet.createRow(rowIndex++);
                int col = 0;

                ExcelUtils.setCell(row, col++, partner.getCode(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getName(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getType().getDescription(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getCeoName(), styles.body());
                ExcelUtils.setCell(row, col++, BusinessNumberFormatter.format(partner.getBusinessNo()), styles.body());
                ExcelUtils.setCell(row, col++, CorporateNumberFormatter.format(partner.getCorporateNo()), styles.body());
                ExcelUtils.setCell(row, col++, partner.getTel(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getFax(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getEmail(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getHomePage(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getZipCode(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getAddress(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getAddressDetail(), styles.body());
                ExcelUtils.setCell(row, col++, partner.getRemark(), styles.body());
            }

            ExcelUtils.autoSizeColumns(sheet, HEADER_COLUMNS.size());

            workbook.write(out);

            return out.toByteArray();

        } catch (Exception e) {
            throw new IllegalStateException("거래처 목록 엑셀 생성 실패", e);
        }
    }

    /**
     * 다운받은 거래처 목록 엑셀 파일 기준으로 업로드
     * 업로드 정책: 신규 등록 / 기존 업데이트
     *
     * @param file - 거래처 목록 엑셀 파일
     */
    @Transactional
    public void upload(MultipartFile file) {
        ExcelValidator.validateFile(file);

        try (InputStream is = file.getInputStream();
             XSSFWorkbook workbook = new XSSFWorkbook(is);) {

            Sheet sheet = workbook.getSheetAt(0);
            Set<String> codeSet = new HashSet<>();

            // 3행부터 데이터
            for (int rowIndex = DATA_START_ROW_INDEX; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);

                if (row == null) {
                    continue;
                }

                String[] values = ExcelUtils.getRowValues(row, HEADER_COLUMNS.size());

                // 빈 행인 경우 건너뛰기
                if (ExcelUtils.isEmptyRow(values)) {
                    continue;
                }

                String code = values[0];
                String name = values[1];

                ExcelValidator.validateRequired(code, "거래처 코드", rowIndex);
                ExcelValidator.validateRequired(name, "거래처명", rowIndex);

                if (!codeSet.add(code)) {
                    throw new BusinessException(
                            "%d행: 중복된 코드입니다. (%s)".formatted(rowIndex + 1, code));
                }

                PartnerType type = null;

                for (PartnerType partnerType : PartnerType.values()) {
                    if (partnerType.getDescription().equals(values[2])) {
                        type = partnerType;
                        break;
                    }
                }

                if (type == null) {
                    throw new BusinessException(
                            "%d행: 올바르지 않은 거래처 유형입니다. (%s)".formatted(rowIndex + 1, values[2]));
                }

                String ceoName = values[3];
                String businessNo = values[4].replaceAll("[^0-9]", "");
                String corporateNo = values[5].replaceAll("[^0-9]", "");
                String tel = values[6];
                String fax = values[7];
                String email = values[8];
                String homePage = values[9];
                String zipCode = values[10];
                String address = values[11];
                String addressDetail = values[12];
                String remark = values[13];

                Partner partner = partnerRepository.findByCodeAndDeletedAtIsNull(code)
                        .orElse(null);

                if (partner == null) {
                    // 신규 거래처 등록
                    partner = new Partner(
                            code,
                            name,
                            type,
                            businessNo,
                            corporateNo,
                            ceoName,
                            tel,
                            fax,
                            email,
                            homePage,
                            zipCode,
                            address,
                            addressDetail,
                            remark
                    );

                    partnerRepository.save(partner);

                } else {
                    // 기존 거래처 수정
                    partner.update(
                            code,
                            name,
                            type,
                            businessNo,
                            corporateNo,
                            ceoName,
                            tel,
                            fax,
                            email,
                            homePage,
                            zipCode,
                            address,
                            addressDetail,
                            remark
                    );
                }
            }

        } catch (BusinessException e) {
            throw e;

        } catch (Exception e) {
            throw new IllegalStateException("거래처 엑셀 업로드 중 오류가 발생했습니다.", e);
        }
    }
}

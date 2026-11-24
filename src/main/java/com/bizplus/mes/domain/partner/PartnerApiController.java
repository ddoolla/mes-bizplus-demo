package com.bizplus.mes.domain.partner;

import com.bizplus.mes.common.message.MessageCode;
import com.bizplus.mes.common.message.MessageService;
import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/partners")
@RequiredArgsConstructor
public class PartnerApiController {

    private final MessageService messageService;
    private final PartnerExcelService partnerExcelService;

    @GetMapping("/excel/download")
    @PreAuthorize("hasAuthority('PARTNER_READ')")
    public ResponseEntity<byte[]> downloadExcel(PartnerSearchDto dto) {
        byte[] data = partnerExcelService.download(dto);

        String fileName = "거래처목록_" + LocalDate.now() + ".xlsx";

        ContentDisposition disposition = ContentDisposition.attachment()
                .filename(fileName, StandardCharsets.UTF_8)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, disposition.toString())
                .contentType(MediaType.parseMediaType(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                ))
                .contentLength(data.length)
                .body(data);
    }

    @PostMapping("/excel/upload")
    @PreAuthorize("hasAuthority('PARTNER_CREATE')")
    public ResponseEntity<ApiResponse<Void>> uploadExcel(MultipartFile file) {
        partnerExcelService.upload(file);

        return ResponseEntity.ok(
                ApiResponse.success(messageService.get(MessageCode.SAVED)));
    }
}

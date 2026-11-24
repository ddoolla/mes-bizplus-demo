package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemExcelService itemExcelService;

    @GetMapping("/excel/download")
    @PreAuthorize("hasAuthority('ITEM_READ')")
    public ResponseEntity<byte[]> downloadExcel(ItemSearchDto dto) {
        byte[] data = itemExcelService.download(dto);

        String fileName = "품목목록_" + LocalDate.now() + ".xlsx";

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

}

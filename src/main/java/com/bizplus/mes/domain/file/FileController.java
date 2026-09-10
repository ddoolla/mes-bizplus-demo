package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.FileResourceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/files")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("/{fileId}")
    public ResponseEntity<Resource> viewFile(@PathVariable Long fileId) {
        FileResourceDto file = fileService.getFileResource(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .body(file.getResource());
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<Resource> downloadFile(@PathVariable Long fileId) {
        FileResourceDto file = fileService.getFileResource(fileId);

        String encodedFilename = UriUtils.encode(
                file.getOriginalName(),
                StandardCharsets.UTF_8
        );

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename*=UTF-8''" + encodedFilename
                )
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .contentLength(file.getSize())
                .body(file.getResource());
    }
}

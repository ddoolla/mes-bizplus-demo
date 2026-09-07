package com.bizplus.mes.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileDto {

    private Long id;
    private String originalName;
    private String storedName;
    private String storagePath;
    private String extension;
    private String contentType;
    private Long size;
}

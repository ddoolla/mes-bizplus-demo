package com.bizplus.mes.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StoredFileDto {

    private String originalName;
    private String storedName;
    private String storagePath;
    private String contentType;
    private String extension;
    private Long size;
}

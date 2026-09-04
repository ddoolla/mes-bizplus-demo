package com.bizplus.mes.domain.file.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.io.Resource;

@Getter
@AllArgsConstructor
public class FileResourceDto {

    private Resource resource;
    private String originalName;
    private String storedName;
    private String storagePath;
    private String contentType;
    private String extension;
    private Long size;
}

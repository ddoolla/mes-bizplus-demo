package com.bizplus.mes.domain.file;

import com.bizplus.mes.domain.file.dto.FileDto;
import com.bizplus.mes.domain.file.dto.FileResourceDto;
import com.bizplus.mes.domain.file.dto.StoredFileDto;
import org.springframework.core.io.Resource;

public class FileMapper {

    public static FileDto toDto(File file) {
        return new FileDto(
                file.getId(),
                file.getOriginalName(),
                file.getStoredName(),
                file.getStoragePath(),
                file.getExtension(),
                file.getContentType(),
                file.getSize()
        );
    }

    public static FileResourceDto toResourceDto(File file, Resource resource) {
        return new FileResourceDto(
                file.getId(),
                file.getOriginalName(),
                file.getStoredName(),
                file.getStoragePath(),
                file.getExtension(),
                file.getContentType(),
                file.getSize(),
                resource
        );
    }

    public static File toEntity(StoredFileDto dto) {
        return new File(
                dto.getOriginalName(),
                dto.getStoredName(),
                dto.getStoragePath(),
                dto.getExtension(),
                dto.getContentType(),
                dto.getSize()
        );
    }
}

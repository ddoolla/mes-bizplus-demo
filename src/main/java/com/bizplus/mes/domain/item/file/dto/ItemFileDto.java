package com.bizplus.mes.domain.item.file.dto;

import com.bizplus.mes.domain.file.FileType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ItemFileDto {

    private final Long id;
    private final FileType type;
    private final Integer sortOrder;
    private final FileInfo file;

    @QueryProjection
    public ItemFileDto(Long id,
                       FileType type,
                       Integer sortOrder,
                       Long fileId,
                       String originalName,
                       String extension) {
        this.id = id;
        this.type = type;
        this.sortOrder = sortOrder;
        this.file = new FileInfo(
                fileId,
                originalName,
                extension
        );
    }

    public record FileInfo(
            Long id,
            String originalName,
            String extension
    ) {
    }
}

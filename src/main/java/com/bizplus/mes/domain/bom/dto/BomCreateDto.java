package com.bizplus.mes.domain.bom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class BomCreateDto {

    @NotNull
    private final Long itemId;

    @NotBlank
    private final String code;

    @NotBlank
    private final String name;
    private final String version;
    private final boolean primary;
    private final String remark;

    public BomCreateDto(Long itemId,
                        String code,
                        String name,
                        String version,
                        Boolean primary,
                        String remark) {
        this.itemId = itemId;
        this.code = code;
        this.name = name;
        this.version = version;
        this.primary = Boolean.TRUE.equals(primary);
        this.remark = remark;
    }
}

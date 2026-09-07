package com.bizplus.mes.domain.item.file.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ItemFileDeleteDto {

    @NotNull
    private Long id;

    @NotNull
    private Long fileId;
}

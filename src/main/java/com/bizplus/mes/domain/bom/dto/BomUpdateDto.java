package com.bizplus.mes.domain.bom.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BomUpdateDto {

    @NotNull
    private Long itemId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String remark;
}

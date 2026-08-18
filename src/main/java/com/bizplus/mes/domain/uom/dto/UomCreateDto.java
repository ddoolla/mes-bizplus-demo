package com.bizplus.mes.domain.uom.dto;

import com.bizplus.mes.domain.uom.UomType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UomCreateDto {

    @NotBlank
    private String code;

    @NotBlank
    private String name;

    @NotNull
    private UomType type;

    private Integer scale;
}

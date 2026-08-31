package com.bizplus.mes.domain.equipment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EquipmentCreateDto {

    private Long typeId;

    @NotBlank
    private String code;

    @NotBlank
    private String name;
    private String specification;
    private String manufacturer;
    private String model;
    private String serialNo;
    private String location;
    private String remark;
}

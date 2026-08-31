package com.bizplus.mes.domain.equipment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EquipmentSearchDto {

    private String code;
    private String name;
    private Long typeId;
}

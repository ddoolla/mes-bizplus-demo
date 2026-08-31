package com.bizplus.mes.domain.equipment.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class EquipmentDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String specification;
    private final String manufacturer;
    private final String model;
    private final String serialNo;
    private final String location;
    private final String remark;
    private final EquipmentTypeCode type;

    @QueryProjection
    public EquipmentDto(Long id,
                        String code,
                        String name,
                        String specification,
                        String manufacturer,
                        String model,
                        String serialNo,
                        String location,
                        String remark,
                        Long typeId,
                        String typeCode,
                        String typeName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.specification = specification;
        this.manufacturer = manufacturer;
        this.model = model;
        this.serialNo = serialNo;
        this.location = location;
        this.remark = remark;
        this.type = new EquipmentTypeCode(typeId, typeCode, typeName);
    }

    public record EquipmentTypeCode(
            Long id,
            String code,
            String name
    ) {
    }
}

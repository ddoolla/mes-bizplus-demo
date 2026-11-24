package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.item.ShelfLifeUnit;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemExcelDto {

    private final String code;
    private final String name;
    private final ItemType type;
    private final String category;
    private final String specification;
    private final String uomCode;
    private final BigDecimal unitPrice;
    private final boolean lotManaged;
    private final Integer shelfLife;
    private final ShelfLifeUnit shelfLifeUnit;
    private final boolean incomingInspectionRequired;
    private final String remark;

    @QueryProjection
    public ItemExcelDto(String code,
                        String name,
                        ItemType type,
                        String category,
                        String specification,
                        String uomCode,
                        BigDecimal unitPrice,
                        boolean lotManaged,
                        Integer shelfLife,
                        ShelfLifeUnit shelfLifeUnit,
                        boolean incomingInspectionRequired,
                        String remark) {
        this.code = code;
        this.name = name;
        this.type = type;
        this.category = category;
        this.specification = specification;
        this.uomCode = uomCode;
        this.unitPrice = unitPrice;
        this.lotManaged = lotManaged;
        this.shelfLife = shelfLife;
        this.shelfLifeUnit = shelfLifeUnit;
        this.incomingInspectionRequired = incomingInspectionRequired;
        this.remark = remark;
    }
}

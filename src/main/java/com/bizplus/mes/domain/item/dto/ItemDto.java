package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.common.dto.IdNameDto;
import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class ItemDto {

    private final Long id;
    private final IdNameDto category;
    private final IdNameDto uom;
    private final String code;
    private final String name;
    private final ItemType type;
    private final String specification;
    private final String remark;
    private final boolean lotManaged;

    @QueryProjection
    public ItemDto(Long id,
                   Long categoryId,
                   String categoryName,
                   Long uomId,
                   String uomCode,
                   String code,
                   String name,
                   ItemType type,
                   String specification,
                   String remark,
                   boolean lotManaged) {
        this.id = id;
        this.category = new IdNameDto(categoryId, categoryName);
        this.uom = new IdNameDto(uomId, uomCode);
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.remark = remark;
        this.lotManaged = lotManaged;
    }
}

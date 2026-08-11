package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.code.common.CommonCode;
import com.bizplus.mes.domain.item.dto.ItemCreateDto;
import com.bizplus.mes.domain.item.dto.ItemUpdateDto;
import com.bizplus.mes.domain.uom.Uom;

public class ItemMapper {

    public static Item toEntity(CommonCode category,
                                Uom uom,
                                ItemCreateDto dto) {

        return new Item(
                category,
                uom,
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getSpecification(),
                dto.getRemark()
        );
    }

    public static void apply(Item item,
                             CommonCode category,
                             Uom uom,
                             ItemUpdateDto dto) {

        item.update(
                category,
                uom,
                dto.getCode(),
                dto.getName(),
                dto.getType(),
                dto.getSpecification(),
                dto.getRemark()
        );
    }
}

package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.bom.item.dto.BomItemUpdateDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.uom.Uom;

import java.math.BigDecimal;

public class BomItemMapper {

    public static BomItem toEntity(Bom bom, Item item, Uom uom) {
        return new BomItem(
                bom,
                item,
                uom,
                BigDecimal.ZERO
        );
    }

    public static void apply(BomItem bomItem, Uom uom, BomItemUpdateDto dto) {
        bomItem.update(
                uom,
                dto.getQuantity()
        );
    }
}

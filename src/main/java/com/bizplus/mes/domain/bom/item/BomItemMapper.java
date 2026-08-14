package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.bom.item.dto.BomItemCreateDto;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.uom.Uom;

public class BomItemMapper {

    public static BomItem toEntity(Bom bom, Item item, Uom uom, BomItemCreateDto dto) {
        return new BomItem(bom, item, uom, dto.getQuantity());
    }
}

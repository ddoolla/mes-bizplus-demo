package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.uom.UomType;
import com.bizplus.mes.domain.uom.dto.UomDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemInventoryDto {

    private final Long id;
    private final String code;
    private final String name;
    private final String category;
    private final ItemType type;
    private final String specification;
    private final UomDto uom;
    private final InventoryInfo inventory;

    @QueryProjection
    public ItemInventoryDto(Long id,
                            String code,
                            String name,
                            String category,
                            ItemType type,
                            String specification,
                            Long uomId,
                            String uomCode,
                            String uomName,
                            UomType uomType,
                            Integer decimalPlaces,
                            Long inventoryId,
                            BigDecimal quantity,
                            BigDecimal reservedQuantity) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.category = category;
        this.type = type;
        this.specification = specification;
        this.uom = new UomDto(uomId, uomCode, uomName, uomType, decimalPlaces);
        this.inventory = new InventoryInfo(inventoryId, quantity, reservedQuantity);
    }

    @Getter
    @AllArgsConstructor
    public static class InventoryInfo {

        private Long id;
        private BigDecimal quantity;
        private BigDecimal reservedQuantity;
    }
}

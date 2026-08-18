package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.uom.UomType;
import com.bizplus.mes.domain.uom.dto.UomDto;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class InventoryDto {

    private final ItemInfo item;
    private final BigDecimal totalQuantity;
    private final BigDecimal totalReservedQuantity;

    @QueryProjection
    public InventoryDto(Long itemId,
                        String itemCode,
                        String itemName,
                        String itemCategory,
                        ItemType itemType,
                        String itemSpec,
                        Long uomId,
                        String uomCode,
                        String uomName,
                        UomType uomType,
                        Integer scale,
                        BigDecimal totalQuantity,
                        BigDecimal totalReservedQuantity) {

        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemCategory,
                itemType,
                itemSpec,
                new UomDto(uomId, uomCode, uomName, uomType, scale)
        );
        this.totalQuantity = totalQuantity;
        this.totalReservedQuantity = totalReservedQuantity;
    }

    @Getter
    @AllArgsConstructor
    public static class ItemInfo {

        private Long id;
        private String code;
        private String name;
        private String category;
        private ItemType type;
        private String specification;
        private UomDto uom;
    }
}

package com.bizplus.mes.domain.inventory.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.lot.ExpirationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class InventoryDto {

    private final Long id;
    private final BigDecimal quantity;
    private final ItemInfo item;
    private final LotInfo lot;

    @QueryProjection
    public InventoryDto(Long id,
                        BigDecimal quantity,
                        Long itemId,
                        String itemCode,
                        String itemName,
                        ItemType itemType,
                        String itemSpec,
                        Long itemUomId,
                        String itemUomCode,
                        Integer itemUomScale,
                        Long lotId,
                        String lotNo,
                        LocalDate manufactureDate,
                        LocalDate expirationDate) {
        this.id = id;
        this.quantity = quantity;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, itemUomScale)
        );
        this.lot = new LotInfo(
                lotId,
                lotNo,
                manufactureDate,
                expirationDate,
                ExpirationStatus.from(expirationDate)
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification,
                           ItemUomInfo uom) {
    }

    public record ItemUomInfo(Long id, String code, Integer scale) {
    }

    public record LotInfo(Long id,
                          String no,
                          LocalDate manufactureDate,
                          LocalDate expirationDate,
                          ExpirationStatus expirationStatus) {
    }
}

package com.bizplus.mes.domain.lot.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.lot.ExpirationStatus;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
public class LotDto {

    private final Long id;
    private final String no;
    private final LocalDate manufactureDate;
    private final LocalDate expirationDate;
    private final String remark;
    private final ItemInfo item;
    private final InventoryInfo inventory;
    private final ExpirationStatus expirationStatus;

    @QueryProjection
    public LotDto(Long id,
                  String no,
                  LocalDate manufactureDate,
                  LocalDate expirationDate,
                  String remark,
                  Long itemId,
                  String itemCode,
                  String itemName,
                  ItemType itemType,
                  String itemSpec,
                  Long itemUomId,
                  String itemUomCode,
                  Integer ItemUomScale,
                  Long inventoryId,
                  BigDecimal quantity) {
        this.id = id;
        this.no = no;
        this.manufactureDate = manufactureDate;
        this.expirationDate = expirationDate;
        this.expirationStatus = ExpirationStatus.from(expirationDate);
        this.remark = remark;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec,
                new ItemUomInfo(itemUomId, itemUomCode, ItemUomScale)
        );
        this.inventory = new InventoryInfo(
                inventoryId,
                quantity
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

    public record InventoryInfo(Long id,
                                BigDecimal quantity) {

    }
}

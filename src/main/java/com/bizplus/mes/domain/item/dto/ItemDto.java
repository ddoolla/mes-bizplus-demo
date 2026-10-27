package com.bizplus.mes.domain.item.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class ItemDto {

    private final Long id;
    private final String code;
    private final String name;
    private final ItemType type;
    private final String specification;
    private final BigDecimal unitPrice;
    private final String remark;
    private final boolean lotManaged;
    private final BigDecimal stock;
    private final CategoryInfo category;
    private final UomInfo uom;
    private final DefaultBomInfo defaultBom;
    private final DefaultRoutingInfo defaultRouting;

    @QueryProjection
    public ItemDto(Long id,
                   String code,
                   String name,
                   ItemType type,
                   String specification,
                   BigDecimal unitPrice,
                   String remark,
                   boolean lotManaged,
                   BigDecimal stock,
                   Long categoryId,
                   String categoryName,
                   Long uomId,
                   String uomCode,
                   Integer uomScale,
                   Long defaultBomId,
                   String defaultBomCode,
                   String defaultBomName,
                   Long defaultRoutingId,
                   String defaultRoutingCode,
                   String defaultRoutingName) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.type = type;
        this.specification = specification;
        this.unitPrice = unitPrice;
        this.remark = remark;
        this.lotManaged = lotManaged;
        this.stock = stock;
        this.category = new CategoryInfo(
                categoryId,
                categoryName
        );
        this.uom = new UomInfo(
                uomId,
                uomCode,
                uomScale
        );
        this.defaultBom = new DefaultBomInfo(
                defaultBomId,
                defaultBomCode,
                defaultBomName
        );
        this.defaultRouting = new DefaultRoutingInfo(
                defaultRoutingId,
                defaultRoutingCode,
                defaultRoutingName
        );
    }

    public record CategoryInfo(Long id, String name) {
    }

    public record UomInfo(Long id, String code, Integer scale) {
    }

    public record DefaultBomInfo(Long id, String code, String name) {
    }

    public record DefaultRoutingInfo(Long id, String code, String name) {
    }
}

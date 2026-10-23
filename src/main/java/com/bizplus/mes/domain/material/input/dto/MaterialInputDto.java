package com.bizplus.mes.domain.material.input.dto;

import com.bizplus.mes.domain.item.ItemType;
import com.bizplus.mes.domain.material.input.MaterialInputStatus;
import com.bizplus.mes.domain.material.input.MaterialInputType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class MaterialInputDto {

    private final Long id;
    private final MaterialInputType type;
    private final BigDecimal quantity;
    private final MaterialInputStatus status;
    private final LocalDateTime inputAt;
    private final ItemInfo item;
    private final UomInfo uom;
    private final LotInfo lot;

    @QueryProjection
    public MaterialInputDto(Long id,
                            MaterialInputType type,
                            BigDecimal quantity,
                            MaterialInputStatus status,
                            LocalDateTime inputAt,
                            Long itemId,
                            String itemCode,
                            String itemName,
                            ItemType itemType,
                            String itemSpec,
                            Long uomId,
                            String uomCode,
                            Integer uomScale,
                            Long lotId,
                            String lotNo) {
        this.id = id;
        this.type = type;
        this.status = status;
        this.quantity = quantity;
        this.inputAt = inputAt;
        this.item = new ItemInfo(
                itemId,
                itemCode,
                itemName,
                itemType,
                itemSpec
        );
        this.uom = new UomInfo(
                uomId,
                uomCode,
                uomScale
        );
        this.lot = new LotInfo(
                lotId,
                lotNo
        );
    }

    public record ItemInfo(Long id,
                           String code,
                           String name,
                           ItemType type,
                           String specification) {
    }

    public record UomInfo(Long id, String code, Integer scale) {
    }

    public record LotInfo(Long id, String no) {
    }
}

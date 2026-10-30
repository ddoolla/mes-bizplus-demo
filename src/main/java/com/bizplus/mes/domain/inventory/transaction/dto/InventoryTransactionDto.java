package com.bizplus.mes.domain.inventory.transaction.dto;

import com.bizplus.mes.domain.inventory.transaction.InventoryReferenceType;
import com.bizplus.mes.domain.inventory.transaction.TransactionType;
import com.bizplus.mes.domain.item.ItemType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class InventoryTransactionDto {

    private final Long id;
    private final TransactionType type;
    private final BigDecimal quantity;
    private final LocalDateTime transactionAt;
    private final InventoryReferenceType referenceType;
    private final Long referenceId;
    private final ItemInfo item;
    private final LotInfo lot;

    @QueryProjection
    public InventoryTransactionDto(Long id,
                                   TransactionType type,
                                   BigDecimal quantity,
                                   LocalDateTime transactionAt,
                                   InventoryReferenceType referenceType,
                                   Long referenceId,
                                   Long itemId,
                                   String itemCode,
                                   String itemName,
                                   ItemType itemType,
                                   String itemSpec,
                                   Long itemUomId,
                                   String itemUomCode,
                                   Integer itemUomScale,
                                   Long lotId,
                                   String lotNo) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.transactionAt = transactionAt;
        this.referenceType = referenceType;
        this.referenceId = referenceId;
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
                lotNo
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

    public record LotInfo(Long id, String no) {
    }
}

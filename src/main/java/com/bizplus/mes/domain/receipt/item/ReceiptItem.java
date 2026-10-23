package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.receipt.Receipt;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "receipt_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReceiptItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receipt_id", nullable = false)
    private Receipt receipt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(precision = 38, scale = 10)
    private BigDecimal quantity;

    private String remark;

    public ReceiptItem(Receipt receipt,
                       Item item,
                       Uom uom,
                       Lot lot,
                       BigDecimal quantity,
                       String remark) {
        this.receipt = receipt;
        this.item = item;
        this.uom = uom;
        this.lot = lot;
        this.quantity = quantity;
        this.remark = remark;
    }

    public void update(Uom uom,
                       BigDecimal quantity,
                       String remark) {
        this.uom = uom;
        this.quantity = quantity;
        this.remark = remark;
    }

    public void updateLot(Lot lot) {
        this.lot = lot;
    }

    public void updateRemark(String remark) {
        this.remark = remark;
    }
}

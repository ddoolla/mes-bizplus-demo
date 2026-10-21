package com.bizplus.mes.domain.material.receipt;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "material_receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MaterialReceipt extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    @Column(unique = true, nullable = false)
    private String receiptNo;

    @Column(nullable = false, precision = 38, scale = 10)
    private BigDecimal quantity;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private MaterialReceiptStatus status;

    private LocalDateTime receiptAt;

    private String remark;

    public MaterialReceipt(Item item,
                           Lot lot,
                           Uom uom,
                           String receiptNo,
                           BigDecimal quantity,
                           MaterialReceiptStatus status,
                           LocalDateTime receiptAt,
                           String remark) {
        this.item = item;
        this.lot = lot;
        this.uom = uom;
        this.receiptNo = receiptNo;
        this.quantity = quantity;
        this.status = status;
        this.receiptAt = receiptAt;
        this.remark = remark;
    }
}

package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "inventory_adjustments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryAdjustment extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    private LocalDate date;

    @Column(precision = 38, scale = 10)
    private BigDecimal beforeQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal afterQuantity;

    private String reason;

    public InventoryAdjustment(Item item,
                               Lot lot,
                               LocalDate date,
                               BigDecimal beforeQuantity,
                               BigDecimal afterQuantity,
                               String reason) {
        this.item = item;
        this.lot = lot;
        this.date = date;
        this.beforeQuantity = beforeQuantity;
        this.afterQuantity = afterQuantity;
        this.reason = reason;
    }
}

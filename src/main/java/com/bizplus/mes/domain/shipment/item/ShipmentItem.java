package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.shipment.Shipment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "shipment_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(precision = 38, scale = 10)
    private BigDecimal quantity;

    public ShipmentItem(Shipment shipment,
                        Item item,
                        Lot lot,
                        BigDecimal quantity) {
        this.shipment = shipment;
        this.item = item;
        this.lot = lot;
        this.quantity = quantity;
    }
}

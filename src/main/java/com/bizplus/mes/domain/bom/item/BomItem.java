package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.common.entity.AuditableEntity;
import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
 * 물리 삭제 데이터
 * */
@Entity
@Table(
        name = "bom_items",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"bom_id", "component_item_id"})
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BomItem extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_id", nullable = false)
    private Bom bom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "component_item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    @Column(precision = 38, scale = 10)
    private BigDecimal quantity;

    public BomItem(Bom bom,
                   Item item,
                   Uom uom,
                   BigDecimal quantity) {
        this.bom = bom;
        this.item = item;
        this.uom = uom;
        this.quantity = quantity;
    }

    public void update(Uom uom, BigDecimal quantity) {
        this.uom = uom;
        this.quantity = quantity;
    }
}

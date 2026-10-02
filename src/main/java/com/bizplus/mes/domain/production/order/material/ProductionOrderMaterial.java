package com.bizplus.mes.domain.production.order.material;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*
* 생산지시 당시 BOM 정보 스냅샷
* */
@Entity
@Table(name = "production_order_materials")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionOrderMaterial extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    // 생산지시 당시 품목 정보 스냅샷
    private String itemCode;
    private String itemName;
    private String itemSpec;

    @Column(precision = 38, scale = 10)
    private BigDecimal requiredQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal consumedQuantity;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsumptionStatus status;

    public ProductionOrderMaterial(ProductionOrder productionOrder, Item item, Uom uom, String itemCode, String itemName, String itemSpec, BigDecimal requiredQuantity, BigDecimal consumedQuantity, ConsumptionStatus status) {
        this.productionOrder = productionOrder;
        this.item = item;
        this.uom = uom;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSpec = itemSpec;
        this.requiredQuantity = requiredQuantity;
        this.consumedQuantity = consumedQuantity;
        this.status = status;
    }
}

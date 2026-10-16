package com.bizplus.mes.domain.production.order.material;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.bom.item.BomItem;
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
    @JoinColumn(name = "bom_item_id", nullable = false)
    private BomItem bomItem; // 원본 참조용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom; // BOM 요구 단위

    /* 생산지시 당시 품목 정보 스냅샷 */
    private Long itemId;
    private String itemCode;
    private String itemName;
    private String itemSpecification;

    @Column(precision = 38, scale = 10)
    private BigDecimal unitRequiredQuantity; // BOM 요구 수량 스냅샷

    @Column(precision = 38, scale = 10)
    private BigDecimal requiredQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal consumedQuantity;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ConsumptionStatus status;

    public ProductionOrderMaterial(ProductionOrder productionOrder,
                                   BomItem bomItem,
                                   Uom uom,
                                   Long itemId,
                                   String itemCode,
                                   String itemName,
                                   String itemSpecification,
                                   BigDecimal unitRequiredQuantity,
                                   BigDecimal requiredQuantity,
                                   BigDecimal consumedQuantity,
                                   ConsumptionStatus status) {
        this.productionOrder = productionOrder;
        this.bomItem = bomItem;
        this.uom = uom;
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemSpecification = itemSpecification;
        this.unitRequiredQuantity = unitRequiredQuantity;
        this.requiredQuantity = requiredQuantity;
        this.consumedQuantity = consumedQuantity;
        this.status = status;
    }

    public void update(BigDecimal requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }
}

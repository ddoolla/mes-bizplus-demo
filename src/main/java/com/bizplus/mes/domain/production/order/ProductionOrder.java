package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.bom.Bom;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.lot.Lot;
import com.bizplus.mes.domain.routing.Routing;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "production_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionOrder extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bom_id", nullable = false)
    private Bom bom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "routing_id", nullable = false)
    private Routing routing;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(unique = true, nullable = false)
    private String orderNo;

    private BigDecimal quantity;

    private LocalDate dueDate;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ProductionOrderStatus status;

    private String remark;

    public ProductionOrder(Item item,
                           Bom bom,
                           Routing routing,
                           String orderNo,
                           BigDecimal quantity,
                           LocalDate dueDate,
                           ProductionOrderStatus status,
                           String remark) {
        this.item = item;
        this.bom = bom;
        this.routing = routing;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.status = status;
        this.remark = remark;
    }

    public void update(BigDecimal quantity,
                       LocalDate dueDate,
                       String remark) {
        this.quantity = quantity;
        this.dueDate = dueDate;
        this.remark = remark;
    }

    public void updateStatus(ProductionOrderStatus status) {
        this.status = status;
    }

    public void updateLot(Lot lot) {
        this.lot = lot;
    }
}

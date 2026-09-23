package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.item.Item;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import com.bizplus.mes.domain.uom.Uom;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "sales_order_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOrderItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id", nullable = false)
    private SalesOrder salesOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id", nullable = false)
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom;

    private BigDecimal unitPrice;
    private BigDecimal quantity;
    private BigDecimal supplyAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;

    @Column(columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private SalesOrderItemStatus status;

    private String remark;

    public SalesOrderItem(SalesOrder salesOrder,
                          Item item,
                          Uom uom,
                          BigDecimal unitPrice,
                          BigDecimal quantity,
                          BigDecimal supplyAmount,
                          BigDecimal taxAmount,
                          BigDecimal totalAmount,
                          SalesOrderItemStatus status,
                          String remark) {
        this.salesOrder = salesOrder;
        this.item = item;
        this.uom = uom;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.supplyAmount = supplyAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.status = status;
        this.remark = remark;
    }

    public void update(BigDecimal unitPrice,
                       BigDecimal quantity,
                       BigDecimal supplyAmount,
                       BigDecimal taxAmount,
                       BigDecimal totalAmount,
                       String remark) {
        this.unitPrice = unitPrice;
        this.quantity = quantity;
        this.supplyAmount = supplyAmount;
        this.taxAmount = taxAmount;
        this.totalAmount = totalAmount;
        this.remark = remark;
    }

    public void updateStatus(SalesOrderItemStatus status) {
        this.status = status;
    }
}

package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.partner.Partner;
import com.bizplus.mes.domain.sales.order.SalesOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "shipments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Shipment extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sales_order_id")
    private SalesOrder salesOrder;

    @Column(unique = true, nullable = false)
    private String shipmentNo;

    @Column(nullable = false)
    private LocalDate date;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ShipmentStatus status;

    private String remark;

    public Shipment(Partner partner,
                    SalesOrder salesOrder,
                    String shipmentNo,
                    LocalDate date,
                    ShipmentStatus status,
                    String remark) {
        this.salesOrder = salesOrder;
        this.shipmentNo = shipmentNo;
        this.partner = partner;
        this.date = date;
        this.status = status;
        this.remark = remark;
    }

    public void update(Partner partner,
                       SalesOrder salesOrder,
                       LocalDate date,
                       String remark) {
        this.salesOrder = salesOrder;
        this.partner = partner;
        this.date = date;
        this.remark = remark;
    }

    public void updateRemark(String remark) {
        this.remark = remark;
    }

    public void complete() {
        this.status = ShipmentStatus.COMPLETED;
    }
}

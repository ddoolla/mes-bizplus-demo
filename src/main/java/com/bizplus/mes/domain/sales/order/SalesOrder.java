package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.partner.Partner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sales_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SalesOrder extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @Column(unique = true, nullable = false)
    private String orderNo;

    private LocalDate date;

    private LocalDate dueDate;

    private String remark;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private SalesOrderStatus status;

    public SalesOrder(Partner partner,
                      String orderNo,
                      LocalDate date,
                      LocalDate dueDate,
                      String remark,
                      SalesOrderStatus status) {
        this.partner = partner;
        this.orderNo = orderNo;
        this.date = date;
        this.dueDate = dueDate;
        this.remark = remark;
        this.status = status;
    }

    public void update(LocalDate date,
                       LocalDate dueDate,
                       String remark) {
        this.date = date;
        this.dueDate = dueDate;
        this.remark = remark;
    }

    public void updatePartner(Partner partner) {
        this.partner = partner;
    }

    public void updateStatus(SalesOrderStatus status) {
        this.status = status;
    }
}

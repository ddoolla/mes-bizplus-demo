package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.partner.Partner;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "receipts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Receipt extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "partner_id", nullable = false)
    private Partner partner;

    @Column(unique = true, nullable = false)
    private String receiptNo;

    private LocalDate date;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private ReceiptStatus status;

    private String remark;

    public Receipt(Partner partner,
                   String receiptNo,
                   LocalDate date,
                   ReceiptStatus status,
                   String remark) {
        this.partner = partner;
        this.receiptNo = receiptNo;
        this.date = date;
        this.status = status;
        this.remark = remark;
    }
}

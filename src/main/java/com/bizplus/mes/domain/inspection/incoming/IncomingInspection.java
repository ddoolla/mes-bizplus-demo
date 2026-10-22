package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.receipt.item.ReceiptItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 입고검사 도메인 = 검사 정보 + 입고 정보
 */
@Entity
@Table(name = "incoming_inspections")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IncomingInspection extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiptItem_id", nullable = false)
    private ReceiptItem receiptItem;

    public IncomingInspection(Inspection inspection, ReceiptItem receiptItem) {
        this.inspection = inspection;
        this.receiptItem = receiptItem;
    }
}

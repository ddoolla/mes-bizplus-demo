package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.work.order.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkResult extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", unique = true, nullable = false)
    private WorkOrder workOrder;

    @Column(precision = 38, scale = 10)
    private BigDecimal productionQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal goodQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal defectQuantity;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    private String remark;

    public WorkResult(WorkOrder workOrder,
                      BigDecimal productionQuantity,
                      BigDecimal goodQuantity,
                      BigDecimal defectQuantity,
                      LocalDateTime startedAt,
                      LocalDateTime completedAt,
                      String remark) {
        this.workOrder = workOrder;
        this.productionQuantity = productionQuantity;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
        this.remark = remark;
    }

    public void stopWork(BigDecimal productionQuantity,
                         BigDecimal goodQuantity,
                         BigDecimal defectQuantity) {
        this.productionQuantity = productionQuantity;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
    }

    public void completeWork(BigDecimal productionQuantity,
                             BigDecimal goodQuantity,
                             BigDecimal defectQuantity,
                             LocalDateTime completedAt,
                             String remark) {
        this.productionQuantity = productionQuantity;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.completedAt = completedAt;
        this.remark = remark;
    }
}

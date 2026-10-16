package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.production.order.ProductionOrder;
import com.bizplus.mes.domain.work.order.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "production_results")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductionResult extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_id", nullable = false)
    private ProductionOrder productionOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder; // 마지막 공정 작업지시 정보

    @Column(precision = 38, scale = 10)
    private BigDecimal totalQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal goodQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal defectQuantity;

    private LocalDateTime completedAt;

    public ProductionResult(ProductionOrder productionOrder,
                            WorkOrder workOrder,
                            BigDecimal totalQuantity,
                            BigDecimal goodQuantity,
                            BigDecimal defectQuantity,
                            LocalDateTime completedAt) {
        this.productionOrder = productionOrder;
        this.workOrder = workOrder;
        this.totalQuantity = totalQuantity;
        this.goodQuantity = goodQuantity;
        this.defectQuantity = defectQuantity;
        this.completedAt = completedAt;
    }
}

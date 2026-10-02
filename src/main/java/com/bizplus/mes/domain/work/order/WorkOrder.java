package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcess;
import com.bizplus.mes.domain.worker.Worker;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WorkOrder extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_order_process_id", nullable = false)
    private ProductionOrderProcess productionOrderProcess;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "worker_id")
    private Worker worker;

    @Column(unique = true, nullable = false)
    private String orderNo;

    @Column(precision = 38, scale = 10)
    private BigDecimal quantity;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    private LocalDateTime startedAt;
    private LocalDateTime completedAt;

    public WorkOrder(ProductionOrderProcess productionOrderProcess,
                     Equipment equipment,
                     Worker worker,
                     String orderNo,
                     BigDecimal quantity,
                     WorkOrderStatus status,
                     LocalDateTime startedAt,
                     LocalDateTime completedAt) {
        this.productionOrderProcess = productionOrderProcess;
        this.equipment = equipment;
        this.worker = worker;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.status = status;
        this.startedAt = startedAt;
        this.completedAt = completedAt;
    }
}


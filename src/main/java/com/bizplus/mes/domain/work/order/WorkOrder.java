package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.production.order.process.ProductionOrderProcess;
import com.bizplus.mes.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    @JoinColumn(name = "user_id")
    private User user;

    @Column(unique = true, nullable = false)
    private String orderNo;

    @Column(precision = 38, scale = 10)
    private BigDecimal quantity;

    private LocalDate date;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private WorkOrderStatus status;

    private String remark;

    public WorkOrder(ProductionOrderProcess productionOrderProcess,
                     Equipment equipment,
                     User user,
                     String orderNo,
                     BigDecimal quantity,
                     LocalDate date,
                     WorkOrderStatus status,
                     String remark) {
        this.productionOrderProcess = productionOrderProcess;
        this.equipment = equipment;
        this.user = user;
        this.orderNo = orderNo;
        this.quantity = quantity;
        this.date = date;
        this.status = status;
        this.remark = remark;
    }

    public void update(Equipment equipment,
                       User user,
                       BigDecimal quantity,
                       LocalDate date,
                       String remark) {
        this.equipment = equipment;
        this.user = user;
        this.quantity = quantity;
        this.date = date;
        this.remark = remark;
    }

    public void assignWorker(User user) {
        this.user = user;
    }

    public void assignEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public void startWork() {
        this.status = WorkOrderStatus.IN_PROGRESS;
    }

    public void completeWork() {
        this.status = WorkOrderStatus.COMPLETED;
    }

    public void stopWork() {
        this.status = WorkOrderStatus.PAUSED;
    }

    public void resumeWork() {
        this.status = WorkOrderStatus.IN_PROGRESS;
    }
}


package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.work.order.WorkOrder;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "in_process_inspections")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InProcessInspection extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "work_order_id", nullable = false)
    private WorkOrder workOrder;

    public InProcessInspection(Inspection inspection, WorkOrder workOrder) {
        this.inspection = inspection;
        this.workOrder = workOrder;
    }
}

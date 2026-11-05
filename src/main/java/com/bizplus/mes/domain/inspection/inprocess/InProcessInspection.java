package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.work.result.WorkResult;
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
    @JoinColumn(name = "work_result_id", nullable = false)
    private WorkResult workResult;

    public InProcessInspection(Inspection inspection, WorkResult workResult) {
        this.inspection = inspection;
        this.workResult = workResult;
    }
}

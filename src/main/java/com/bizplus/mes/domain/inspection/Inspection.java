package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "inspections")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Inspection extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_spec_id", nullable = false)
    private InspectionSpec inspectionSpec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_id")
    private User inspector;

    @Column(unique = true, nullable = false)
    private String inspectionNo;

    private LocalDate date;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private InspectionType type;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private InspectionStatus status;

    @Column(columnDefinition = "varchar(255)")
    @Enumerated(EnumType.STRING)
    private InspectionJudgement judgement;

    private String remark;

    public Inspection(InspectionSpec inspectionSpec,
                      User inspector,
                      String inspectionNo,
                      InspectionType type,
                      InspectionStatus status,
                      InspectionJudgement judgement,
                      LocalDate date,
                      String remark) {
        this.inspectionSpec = inspectionSpec;
        this.inspector = inspector;
        this.inspectionNo = inspectionNo;
        this.type = type;
        this.status = status;
        this.judgement = judgement;
        this.date = date;
        this.remark = remark;
    }
}

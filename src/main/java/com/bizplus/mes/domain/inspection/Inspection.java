package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import com.bizplus.mes.domain.uom.Uom;
import com.bizplus.mes.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "uom_id", nullable = false)
    private Uom uom; // 검사 단위

    @Column(unique = true, nullable = false)
    private String inspectionNo;

    private LocalDate date;

    @Column(precision = 38, scale = 10)
    private BigDecimal inspectedQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal passedQuantity;

    @Column(precision = 38, scale = 10)
    private BigDecimal failedQuantity;

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
                      Uom uom,
                      String inspectionNo,
                      InspectionType type,
                      InspectionStatus status,
                      InspectionJudgement judgement,
                      LocalDate date,
                      BigDecimal inspectedQuantity,
                      BigDecimal passedQuantity,
                      BigDecimal failedQuantity,
                      String remark) {
        this.inspectionSpec = inspectionSpec;
        this.inspector = inspector;
        this.uom = uom;
        this.inspectionNo = inspectionNo;
        this.type = type;
        this.status = status;
        this.judgement = judgement;
        this.date = date;
        this.inspectedQuantity = inspectedQuantity;
        this.passedQuantity = passedQuantity;
        this.failedQuantity = failedQuantity;
        this.remark = remark;
    }

    public void update(LocalDate date,
                       BigDecimal inspectedQuantity,
                       String remark) {
        this.date = date;
        this.inspectedQuantity = inspectedQuantity;
        this.remark = remark;
    }

    public void updateRemark(String remark) {
        this.remark = remark;
    }
}

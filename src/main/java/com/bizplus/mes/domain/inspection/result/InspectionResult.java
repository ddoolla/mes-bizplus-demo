package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.Inspection;
import com.bizplus.mes.domain.inspection.InspectionJudgement;
import com.bizplus.mes.domain.inspection.spec.item.InspectionSpecItem;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inspection_result")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InspectionResult extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_id", nullable = false)
    private Inspection inspection;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_spec_item_id", nullable = false)
    private InspectionSpecItem inspectionSpecItem; // 원본 참조 값

    // 검사항목, 기준 스냅샷
    private Long itemId;
    private String itemGroup;
    private String itemCode;
    private String itemName;
    private String standard;
    private String method;
    private String specRemark;
    private Integer sortOrder;

    // 측정 값
    private String measuredValue;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private InspectionJudgement judgement;

    private String remark;

    public InspectionResult(Inspection inspection,
                            InspectionSpecItem inspectionSpecItem,
                            Long itemId,
                            String itemGroup,
                            String itemCode,
                            String itemName,
                            String standard,
                            String method,
                            String specRemark,
                            Integer sortOrder,
                            String measuredValue,
                            InspectionJudgement judgement,
                            String remark) {
        this.inspection = inspection;
        this.inspectionSpecItem = inspectionSpecItem;
        this.itemId = itemId;
        this.itemGroup = itemGroup;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.standard = standard;
        this.method = method;
        this.specRemark = specRemark;
        this.sortOrder = sortOrder;
        this.measuredValue = measuredValue;
        this.judgement = judgement;
        this.remark = remark;
    }
}

package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.common.entity.SoftDeletableEntity;
import com.bizplus.mes.domain.inspection.item.InspectionItem;
import com.bizplus.mes.domain.inspection.spec.InspectionSpec;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "inspection_spec_items")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InspectionSpecItem extends SoftDeletableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_spec_id", nullable = false)
    private InspectionSpec inspectionSpec;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspection_item_id", nullable = false)
    private InspectionItem inspectionItem;

    private Integer sortOrder;

    private String standard;

    private String method;

    private String remark;

    public InspectionSpecItem(InspectionSpec inspectionSpec,
                              InspectionItem inspectionItem,
                              Integer sortOrder,
                              String standard,
                              String method,
                              String remark) {
        this.inspectionSpec = inspectionSpec;
        this.inspectionItem = inspectionItem;
        this.sortOrder = sortOrder;
        this.standard = standard;
        this.method = method;
        this.remark = remark;
    }

    public void update(Integer sortOrder,
                       String standard,
                       String method,
                       String remark) {
        this.sortOrder = sortOrder;
        this.standard = standard;
        this.method = method;
        this.remark = remark;
    }
}

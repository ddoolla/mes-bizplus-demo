package com.bizplus.mes.domain.inspection.spec.item.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class InspectionSpecItemDto {

    private final Long id;
    private final Integer sortOrder;
    private final String standard;
    private final String method;
    private final String remark;
    private final InspectionItemInfo inspectionItem;

    @QueryProjection
    public InspectionSpecItemDto(Long id,
                                 Integer sortOrder,
                                 String standard,
                                 String method,
                                 String remark,
                                 Long inspectionItemId,
                                 String inspectionItemGroup,
                                 String inspectionItemCode,
                                 String inspectionItemName,
                                 String inspectionItemDescription) {
        this.id = id;
        this.sortOrder = sortOrder;
        this.standard = standard;
        this.method = method;
        this.remark = remark;
        this.inspectionItem = new InspectionItemInfo(
                inspectionItemId,
                inspectionItemGroup,
                inspectionItemCode,
                inspectionItemName,
                inspectionItemDescription
        );
    }

    public record InspectionItemInfo(
            Long id,
            String group,
            String code,
            String name,
            String description
    ) {
    }
}

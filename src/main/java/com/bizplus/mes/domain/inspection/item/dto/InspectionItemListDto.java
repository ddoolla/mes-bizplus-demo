package com.bizplus.mes.domain.inspection.item.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InspectionItemListDto {

    private List<InspectionItemDto> inspectionItems;
    private Pagination pagination;
}

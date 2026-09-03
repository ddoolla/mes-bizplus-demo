package com.bizplus.mes.domain.defect.item.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class DefectItemListDto {

    private List<DefectItemDto> defectItems;
    private Pagination pagination;
}

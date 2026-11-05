package com.bizplus.mes.domain.inspection.inprocess.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InProcessInspectionListDto {

    private List<InProcessInspectionSummaryDto> inProcessInspections;
    private Pagination pagination;
}

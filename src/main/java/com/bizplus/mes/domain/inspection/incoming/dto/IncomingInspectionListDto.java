package com.bizplus.mes.domain.inspection.incoming.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class IncomingInspectionListDto {

    private List<IncomingInspectionSummaryDto> incomingInspections;
    private Pagination pagination;
}

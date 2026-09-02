package com.bizplus.mes.domain.inspection.spec.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InspectionSpecListDto {

    private List<InspectionSpecDto> inspectionSpecs;
    private Pagination pagination;
}

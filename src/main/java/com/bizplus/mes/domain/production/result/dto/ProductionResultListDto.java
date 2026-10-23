package com.bizplus.mes.domain.production.result.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProductionResultListDto {

    private List<ProductionResultDto> productionResults;
    private Pagination pagination;
}

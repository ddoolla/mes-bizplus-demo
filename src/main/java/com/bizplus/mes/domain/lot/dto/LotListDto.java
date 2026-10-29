package com.bizplus.mes.domain.lot.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class LotListDto {

    private List<LotDto> lots;
    private Pagination pagination;
}

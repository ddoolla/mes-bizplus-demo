package com.bizplus.mes.domain.routing.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RoutingListDto {

    private List<RoutingDto> routings;
    private Pagination pagination;
}

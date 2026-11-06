package com.bizplus.mes.domain.work.result.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkResultListDto {

    private List<WorkResultDto> workResults;
    private Pagination pagination;
}

package com.bizplus.mes.domain.worker.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class WorkerListDto {

    private List<WorkerDto> workers;
    private Pagination pagination;
}

package com.bizplus.mes.domain.process.dto;

import com.bizplus.mes.common.pagination.Pagination;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ProcessListDto {

    private List<ProcessDto> processes;
    private Pagination pagination;
}

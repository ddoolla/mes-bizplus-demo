package com.bizplus.mes.domain.worker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class WorkerSearchDto {

    private String code;
    private String userName;
    private Long departmentId;
    private Long positionId;
}

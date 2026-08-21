package com.bizplus.mes.domain.routing.process.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class RoutingProcessDto {

    private final Long id;
    private final int stepNo;
    private final ProcessInfo process;

    @QueryProjection
    public RoutingProcessDto(Long id,
                             int stepNo,
                             Long processId,
                             String processCode,
                             String processName) {
        this.id = id;
        this.stepNo = stepNo;
        this.process = new ProcessInfo(processId, processCode, processName);
    }

    public record ProcessInfo(
            Long id,
            String code,
            String name
    ) {
    }
}

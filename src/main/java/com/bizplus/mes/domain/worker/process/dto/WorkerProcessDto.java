package com.bizplus.mes.domain.worker.process.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class WorkerProcessDto {

    private final Long id;
    private final ProcessInfo process;

    @QueryProjection
    public WorkerProcessDto(Long id,
                            Long processId,
                            String processCode,
                            String processName,
                            String processDescription) {
        this.id = id;
        this.process = new ProcessInfo(
                processId,
                processCode,
                processName,
                processDescription);
    }

    public record ProcessInfo(
            Long id,
            String code,
            String name,
            String description
    ) {
    }
}

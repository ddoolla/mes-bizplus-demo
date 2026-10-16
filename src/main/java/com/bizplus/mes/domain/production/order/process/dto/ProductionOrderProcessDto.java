package com.bizplus.mes.domain.production.order.process.dto;

import lombok.Getter;

@Getter
public class ProductionOrderProcessDto {

    private final Long id;
    private final ProcessInfo process;

    public ProductionOrderProcessDto(Long id,
                                     Long processId,
                                     String processCode,
                                     String processName,
                                     int processStepNo) {
        this.id = id;
        this.process = new ProcessInfo(
                processId,
                processCode,
                processName,
                processStepNo
        );
    }

    public record ProcessInfo(
            Long id,
            String code,
            String name,
            int stepNo
    ) {
    }
}

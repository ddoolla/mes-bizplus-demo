package com.bizplus.mes.domain.routing.process.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class RoutingProcessDto {

    private final Long id;
    private final int stepNo;
    private final boolean processInspectionRequired;
    private final ProcessInfo process;
    private final InspectionSpecInfo defaultIpqcSpec;

    @QueryProjection
    public RoutingProcessDto(Long id,
                             int stepNo,
                             boolean processInspectionRequired,
                             Long processId,
                             String processCode,
                             String processName,
                             String processDescription,
                             Long inspectionSpecId,
                             String inspectionSpecName) {
        this.id = id;
        this.stepNo = stepNo;
        this.processInspectionRequired = processInspectionRequired;
        this.process = new ProcessInfo(
                processId,
                processCode,
                processName,
                processDescription);
        this.defaultIpqcSpec = new InspectionSpecInfo(
                inspectionSpecId,
                inspectionSpecName
        );
    }

    public record ProcessInfo(Long id,
                              String code,
                              String name,
                              String description) {
    }

    public record InspectionSpecInfo(Long id, String name) {
    }
}

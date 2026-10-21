package com.bizplus.mes.domain.inspection;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InspectionType {

    INCOMING("수입검사"), // Incoming Quality Control (IQC)
    IN_PROCESS("공정검사"), // In Process Quality Control (IPQC)
    OUTGOING("출하검사"); // Outgoing Quality Control (OQC)

    private final String description;
}

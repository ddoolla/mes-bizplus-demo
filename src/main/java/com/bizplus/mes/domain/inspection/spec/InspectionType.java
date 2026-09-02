package com.bizplus.mes.domain.inspection.spec;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum InspectionType {

    INCOMING("수입검사"),
    PROCESS("공정검사"),
    SHIPPING("출하검사");

    private final String description;
}

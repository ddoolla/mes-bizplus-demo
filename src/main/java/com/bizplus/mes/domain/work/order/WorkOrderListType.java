package com.bizplus.mes.domain.work.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public enum WorkOrderListType {

    ACTIVE(
            "진행 작업",
            List.of(WorkOrderStatus.PENDING, WorkOrderStatus.IN_PROGRESS)
    ),
    COMPLETED(
            "완료 작업",
            List.of(WorkOrderStatus.COMPLETED)
    );

    private final String description;
    private final List<WorkOrderStatus> statuses;
}

package com.bizplus.mes.domain.work.order;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum WorkOrderStatus {

    DRAFT("작성중"),
    PENDING("대기"),
    IN_PROGRESS("작업중"),
    COMPLETED("완료");

    private final String description;
}

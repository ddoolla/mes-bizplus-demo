package com.bizplus.mes.domain.work.order;

import java.time.LocalDate;

public interface WorkOrderQueryRepository {

    String findMaxOrderNo(LocalDate date);
}

package com.bizplus.mes.domain.work.order;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.bizplus.mes.common.util.PredicateUtils.endDateLoe;
import static com.bizplus.mes.common.util.PredicateUtils.startDateGoe;
import static com.bizplus.mes.domain.work.order.QWorkOrder.workOrder;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkOrderQueryRepositoryImpl implements WorkOrderQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public String findMaxOrderNo(LocalDate date) {
        return query
                .select(workOrder.orderNo.max())
                .from(workOrder)
                .where(
                        startDateGoe(workOrder.createdAt, date),
                        endDateLoe(workOrder.createdAt, date)
                )
                .fetchFirst();
    }
}

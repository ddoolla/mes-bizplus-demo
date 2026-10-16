package com.bizplus.mes.domain.production.order.process;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductionOrderProcessQueryRepositoryImpl implements ProductionOrderProcessQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Integer findLastStepNo(Long productionOrderId) {
        return query
                .select(productionOrderProcess.processStepNo.max())
                .from(productionOrderProcess)
                .where(
                        notDeleted(productionOrderProcess.deletedAt),
                        eq(productionOrderProcess.productionOrder.id, productionOrderId)
                )
                .fetchFirst();
    }
}

package com.bizplus.mes.domain.production.order.process;

import com.bizplus.mes.domain.production.order.process.dto.ProductionOrderProcessDto;
import com.bizplus.mes.domain.production.order.process.dto.QProductionOrderProcessDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductionOrderProcessQueryRepositoryImpl implements ProductionOrderProcessQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<ProductionOrderProcessDto> fetchAll(Long productionOrderId) {
        return query
                .select(new QProductionOrderProcessDto(
                        productionOrderProcess.id,
                        productionOrderProcess.processId,
                        productionOrderProcess.processCode,
                        productionOrderProcess.processName,
                        productionOrderProcess.processStepNo
                ))
                .from(productionOrderProcess)
                .where(
                        notDeleted(productionOrderProcess.deletedAt),
                        eq(productionOrderProcess.productionOrder.id, productionOrderId)
                )
                .orderBy(productionOrderProcess.processStepNo.asc())
                .fetch();
    }

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

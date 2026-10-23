package com.bizplus.mes.domain.production.result;

import com.bizplus.mes.domain.production.result.dto.ProductionResultDto;
import com.bizplus.mes.domain.production.result.dto.ProductionResultSearchDto;
import com.bizplus.mes.domain.production.result.dto.QProductionResultDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.production.result.QProductionResult.productionResult;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductionResultQueryRepositoryImpl implements ProductionResultQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<ProductionResultDto> fetchProductionResults(ProductionResultSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(contains(productionOrder.orderNo, dto.getProductionOrderNo()))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(startDateGoe(productionResult.completedAt, dto.getStartDate()))
                .and(endDateLoe(productionResult.completedAt, dto.getEndDate()));

        List<ProductionResultDto> content = query
                .select(new QProductionResultDto(
                        productionResult.id,
                        productionResult.totalQuantity,
                        productionResult.goodQuantity,
                        productionResult.defectQuantity,
                        productionResult.completedAt,
                        productionOrder.id,
                        productionOrder.orderNo,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale
                ))
                .from(productionResult)
                .innerJoin(productionOrder).on(productionResult.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .orderBy(productionResult.completedAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(productionResult.count())
                .from(productionResult)
                .innerJoin(productionOrder).on(productionResult.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

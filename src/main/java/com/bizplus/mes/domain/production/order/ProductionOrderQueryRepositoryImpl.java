package com.bizplus.mes.domain.production.order;

import com.bizplus.mes.domain.production.order.dto.ProductionOrderDto;
import com.bizplus.mes.domain.production.order.dto.ProductionOrderSearchDto;
import com.bizplus.mes.domain.production.order.dto.QProductionOrderDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductionOrderQueryRepositoryImpl implements ProductionOrderQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<ProductionOrderDto> findProductionOrders(ProductionOrderSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(productionOrder.deletedAt))
                .and(contains(productionOrder.orderNo, dto.getOrderNo()))
                .and(containsAny(dto.getItemName(), item.code, item.name))
                .and(startDateGoe(productionOrder.dueDate, dto.getStartDate()))
                .and(endDateLoe(productionOrder.dueDate, dto.getEndDate()));

        List<ProductionOrderDto> content = query
                .select(new QProductionOrderDto(
                        productionOrder.id,
                        productionOrder.orderNo,
                        productionOrder.quantity,
                        productionOrder.dueDate,
                        productionOrder.status,
                        productionOrder.remark,
                        item.id,
                        item.code,
                        item.name,
                        item.specification,
                        uom.code,
                        uom.scale
                ))
                .from(productionOrder)
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .orderBy(productionOrder.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(productionOrder.count())
                .from(productionOrder)
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<ProductionOrderDto> findProductionOrder(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QProductionOrderDto(
                                productionOrder.id,
                                productionOrder.orderNo,
                                productionOrder.quantity,
                                productionOrder.dueDate,
                                productionOrder.status,
                                productionOrder.remark,
                                item.id,
                                item.code,
                                item.name,
                                item.specification,
                                uom.code,
                                uom.scale
                        ))
                        .from(productionOrder)
                        .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                        .innerJoin(uom).on(item.uom.id.eq(uom.id))
                        .where(
                                notDeleted(productionOrder.deletedAt),
                                eq(productionOrder.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public String findMaxOrderNo(LocalDate date) {
        return query
                .select(productionOrder.orderNo.max())
                .from(productionOrder)
                .where(
                        startDateGoe(productionOrder.createdAt, date),
                        endDateLoe(productionOrder.createdAt, date)
                )
                .fetchFirst();
    }
}

package com.bizplus.mes.domain.sales.order;

import com.bizplus.mes.domain.sales.order.dto.QSalesOrderDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderDto;
import com.bizplus.mes.domain.sales.order.dto.SalesOrderSearchDto;
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
import static com.bizplus.mes.domain.partner.QPartner.partner;
import static com.bizplus.mes.domain.sales.order.QSalesOrder.salesOrder;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesOrderQueryRepositoryImpl implements SalesOrderQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<SalesOrderDto> findSalesOrders(SalesOrderSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(salesOrder.deletedAt))
                .and(contains(salesOrder.orderNo, dto.getOrderNo()))
                .and(contains(partner.name, dto.getPartnerName()))
                .and(startDateGoe(salesOrder.dueDate, dto.getStartDate()))
                .and(endDateLoe(salesOrder.dueDate, dto.getEndDate()));

        List<SalesOrderDto> content = query
                .select(new QSalesOrderDto(
                        salesOrder.id,
                        salesOrder.orderNo,
                        salesOrder.date,
                        salesOrder.dueDate,
                        salesOrder.remark,
                        salesOrder.status,
                        partner.id,
                        partner.name
                ))
                .from(salesOrder)
                .innerJoin(partner).on(salesOrder.partner.id.eq(partner.id))
                .where(searchCondition)
                .orderBy(salesOrder.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(salesOrder.count())
                .from(salesOrder)
                .innerJoin(partner).on(salesOrder.partner.id.eq(partner.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<SalesOrderDto> findSalesOrder(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QSalesOrderDto(
                                salesOrder.id,
                                salesOrder.orderNo,
                                salesOrder.date,
                                salesOrder.dueDate,
                                salesOrder.remark,
                                salesOrder.status,
                                partner.id,
                                partner.name
                        ))
                        .from(salesOrder)
                        .innerJoin(partner).on(salesOrder.partner.id.eq(partner.id))
                        .where(
                                notDeleted(salesOrder.deletedAt),
                                eq(salesOrder.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public String findMaxOrderNo(LocalDate date) {
        return query
                .select(salesOrder.orderNo.max())
                .from(salesOrder)
                .where(
                        startDateGoe(salesOrder.createdAt, date),
                        endDateLoe(salesOrder.createdAt, date)
                )
                .fetchFirst();
    }
}

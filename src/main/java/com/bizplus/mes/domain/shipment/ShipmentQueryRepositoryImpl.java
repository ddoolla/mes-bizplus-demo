package com.bizplus.mes.domain.shipment;

import com.bizplus.mes.domain.shipment.dto.QShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentDto;
import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.partner.QPartner.partner;
import static com.bizplus.mes.domain.sales.order.QSalesOrder.salesOrder;
import static com.bizplus.mes.domain.shipment.QShipment.shipment;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShipmentQueryRepositoryImpl implements ShipmentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<ShipmentDto> fetchShipments(ShipmentSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(shipment.deletedAt))
                .and(contains(shipment.shipmentNo, dto.getShipmentNo()))
                .and(containsAny(dto.getPartner(), partner.code, partner.name))
                .and(startDateGoe(shipment.date, dto.getStartDate()))
                .and(endDateLoe(shipment.date, dto.getEndDate()));

        List<ShipmentDto> content = query
                .select(new QShipmentDto(
                        shipment.id,
                        shipment.shipmentNo,
                        shipment.date,
                        shipment.status,
                        shipment.remark,
                        partner.id,
                        partner.name,
                        salesOrder.id,
                        salesOrder.orderNo
                ))
                .from(shipment)
                .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                .leftJoin(salesOrder).on(shipment.salesOrder.id.eq(salesOrder.id))
                .where(searchCondition)
                .orderBy(shipment.date.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(shipment.count())
                .from(shipment)
                .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                .leftJoin(salesOrder).on(shipment.salesOrder.id.eq(salesOrder.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<ShipmentDto> fetchShipment(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QShipmentDto(
                                shipment.id,
                                shipment.shipmentNo,
                                shipment.date,
                                shipment.status,
                                shipment.remark,
                                partner.id,
                                partner.name,
                                salesOrder.id,
                                salesOrder.orderNo
                        ))
                        .from(shipment)
                        .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                        .leftJoin(salesOrder).on(shipment.salesOrder.id.eq(salesOrder.id))
                        .where(
                                notDeleted(shipment.deletedAt),
                                eq(shipment.id, id)
                        )
                        .fetchOne()
        );
    }
}

package com.bizplus.mes.domain.work.order;

import com.bizplus.mes.domain.production.order.ProductionOrderStatus;
import com.bizplus.mes.domain.work.order.dto.QWorkOrderDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderDto;
import com.bizplus.mes.domain.work.order.dto.WorkOrderSearchDto;
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
import static com.bizplus.mes.domain.equipment.QEquipment.equipment;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;
import static com.bizplus.mes.domain.uom.QUom.uom;
import static com.bizplus.mes.domain.user.QUser.user;
import static com.bizplus.mes.domain.work.order.QWorkOrder.workOrder;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkOrderQueryRepositoryImpl implements WorkOrderQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<WorkOrderDto> findWorkOrders(WorkOrderListType listType,
                                             WorkOrderSearchDto dto,
                                             Pageable pageable) {
        return findWorkOrders(listType, null, dto, pageable);
    }

    @Override
    public Page<WorkOrderDto> findWorkOrdersByUserId(WorkOrderListType listType,
                                                     Long userId,
                                                     WorkOrderSearchDto dto,
                                                     Pageable pageable) {
        return findWorkOrders(listType, userId, dto, pageable);
    }

    private Page<WorkOrderDto> findWorkOrders(WorkOrderListType listType,
                                              Long userId,
                                              WorkOrderSearchDto dto,
                                              Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(workOrder.deletedAt))
                .and(productionOrder.status.ne(ProductionOrderStatus.DRAFT))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(containsAny(dto.getProcess(), productionOrderProcess.code, productionOrderProcess.name))
                .and(eq(workOrder.status, dto.getStatus()))
                .and(startDateGoe(workOrder.date, dto.getStartDate()))
                .and(endDateLoe(workOrder.date, dto.getEndDate()));

        if (userId != null) {
            searchCondition.and(eq(workOrder.user.id, userId));
        }

        if (listType != null) {
            searchCondition.and(in(workOrder.status, listType.getStatuses()));
        }

        List<WorkOrderDto> content = query
                .select(new QWorkOrderDto(
                        workOrder.id,
                        workOrder.orderNo,
                        workOrder.quantity,
                        workOrder.date,
                        workOrder.status,
                        workOrder.startedAt,
                        workOrder.completedAt,
                        workOrder.remark,
                        productionOrderProcess.id,
                        productionOrderProcess.code,
                        productionOrderProcess.name,
                        equipment.id,
                        equipment.code,
                        equipment.name,
                        user.id,
                        user.name,
                        item.id,
                        item.code,
                        item.name,
                        item.specification,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(workOrder)
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                .leftJoin(user).on(workOrder.user.id.eq(user.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .orderBy(workOrder.date.asc(), productionOrderProcess.stepNo.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(workOrder.count())
                .from(workOrder)
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                .leftJoin(user).on(workOrder.user.id.eq(user.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public List<WorkOrderDto> findWorkOrders(Long productionOrderId) {
        return query
                .select(new QWorkOrderDto(
                        workOrder.id,
                        workOrder.orderNo,
                        workOrder.quantity,
                        workOrder.date,
                        workOrder.status,
                        workOrder.startedAt,
                        workOrder.completedAt,
                        workOrder.remark,
                        productionOrderProcess.id,
                        productionOrderProcess.code,
                        productionOrderProcess.name,
                        equipment.id,
                        equipment.code,
                        equipment.name,
                        user.id,
                        user.name,
                        item.id,
                        item.code,
                        item.name,
                        item.specification,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(workOrder)
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                .leftJoin(user).on(workOrder.user.id.eq(user.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(
                        notDeleted(workOrder.deletedAt),
                        eq(productionOrder.id, productionOrderId)
                )
                .orderBy(productionOrderProcess.stepNo.asc())
                .fetch();
    }

    @Override
    public Optional<WorkOrderDto> findWorkOrder(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QWorkOrderDto(
                                workOrder.id,
                                workOrder.orderNo,
                                workOrder.quantity,
                                workOrder.date,
                                workOrder.status,
                                workOrder.startedAt,
                                workOrder.completedAt,
                                workOrder.remark,
                                productionOrderProcess.id,
                                productionOrderProcess.code,
                                productionOrderProcess.name,
                                equipment.id,
                                equipment.code,
                                equipment.name,
                                user.id,
                                user.name,
                                item.id,
                                item.code,
                                item.name,
                                item.specification,
                                uom.code,
                                uom.scale,
                                lot.id,
                                lot.no
                        ))
                        .from(workOrder)
                        .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                        .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                        .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                        .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                        .leftJoin(user).on(workOrder.user.id.eq(user.id))
                        .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                        .innerJoin(uom).on(item.uom.id.eq(uom.id))
                        .where(
                                notDeleted(workOrder.deletedAt),
                                eq(workOrder.id, id)
                        )
                        .fetchOne()
        );
    }

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

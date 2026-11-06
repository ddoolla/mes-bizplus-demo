package com.bizplus.mes.domain.work.result;

import com.bizplus.mes.domain.work.result.dto.QWorkResultDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultDto;
import com.bizplus.mes.domain.work.result.dto.WorkResultSearchDto;
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
import static com.bizplus.mes.domain.equipment.QEquipment.equipment;
import static com.bizplus.mes.domain.inspection.spec.QInspectionSpec.inspectionSpec;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;
import static com.bizplus.mes.domain.routing.process.QRoutingProcess.routingProcess;
import static com.bizplus.mes.domain.uom.QUom.uom;
import static com.bizplus.mes.domain.user.QUser.user;
import static com.bizplus.mes.domain.work.order.QWorkOrder.workOrder;
import static com.bizplus.mes.domain.work.result.QWorkResult.workResult;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkResultQueryRepositoryImpl implements WorkResultQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<WorkResultDto> fetchAll(WorkResultSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(workResult.deletedAt))
                .and(notDeleted(workOrder.deletedAt))
                .and(contains(workOrder.orderNo, dto.getWorkOrderNo()))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(startDateGoe(workOrder.date, dto.getStartDate()))
                .and(endDateLoe(workOrder.date, dto.getEndDate()));

        List<WorkResultDto> content = query
                .select(new QWorkResultDto(
                        workResult.id,
                        workResult.productionQuantity,
                        workResult.goodQuantity,
                        workResult.defectQuantity,
                        workResult.startedAt,
                        workResult.completedAt,
                        workResult.remark,
                        workOrder.id,
                        workOrder.orderNo,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale,
                        productionOrderProcess.processId,
                        productionOrderProcess.processName,
                        inspectionSpec.id,
                        inspectionSpec.name,
                        equipment.id,
                        equipment.name,
                        user.id,
                        user.name,
                        lot.id,
                        lot.no
                ))
                .from(workResult)
                .innerJoin(workOrder).on(workResult.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(routingProcess).on(productionOrderProcess.routingProcess.id.eq(routingProcess.id))
                .leftJoin(inspectionSpec).on(routingProcess.defaultIpqcSpec.id.eq(inspectionSpec.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                .leftJoin(user).on(workOrder.user.id.eq(user.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(workOrder.orderNo.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(workResult.count())
                .from(workResult)
                .innerJoin(workOrder).on(workResult.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(routingProcess).on(productionOrderProcess.routingProcess.id.eq(routingProcess.id))
                .leftJoin(inspectionSpec).on(routingProcess.defaultIpqcSpec.id.eq(inspectionSpec.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                .leftJoin(user).on(workOrder.user.id.eq(user.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<WorkResultDto> fetchById(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QWorkResultDto(
                                workResult.id,
                                workResult.productionQuantity,
                                workResult.goodQuantity,
                                workResult.defectQuantity,
                                workResult.startedAt,
                                workResult.completedAt,
                                workResult.remark,
                                workOrder.id,
                                workOrder.orderNo,
                                item.id,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                uom.id,
                                uom.code,
                                uom.scale,
                                productionOrderProcess.processId,
                                productionOrderProcess.processName,
                                inspectionSpec.id,
                                inspectionSpec.name,
                                equipment.id,
                                equipment.name,
                                user.id,
                                user.name,
                                lot.id,
                                lot.no
                        ))
                        .from(workResult)
                        .innerJoin(workOrder).on(workResult.workOrder.id.eq(workOrder.id))
                        .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                        .innerJoin(routingProcess).on(productionOrderProcess.routingProcess.id.eq(routingProcess.id))
                        .leftJoin(inspectionSpec).on(routingProcess.defaultIpqcSpec.id.eq(inspectionSpec.id))
                        .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                        .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                        .innerJoin(uom).on(item.uom.id.eq(uom.id))
                        .leftJoin(equipment).on(workOrder.equipment.id.eq(equipment.id))
                        .leftJoin(user).on(workOrder.user.id.eq(user.id))
                        .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                        .where(
                                notDeleted(workResult.deletedAt),
                                notDeleted(workOrder.deletedAt),
                                eq(workResult.id, id)
                        )
                        .fetchOne()
        );
    }
}

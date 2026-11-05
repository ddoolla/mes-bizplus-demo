package com.bizplus.mes.domain.inspection.inprocess;

import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSearchDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.InProcessInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.inprocess.dto.QInProcessInspectionSummaryDto;
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
import static com.bizplus.mes.domain.inspection.QInspection.inspection;
import static com.bizplus.mes.domain.inspection.inprocess.QInProcessInspection.inProcessInspection;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;
import static com.bizplus.mes.domain.uom.QUom.uom;
import static com.bizplus.mes.domain.work.order.QWorkOrder.workOrder;
import static com.bizplus.mes.domain.work.result.QWorkResult.workResult;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InProcessInspectionQueryRepositoryImpl implements InProcessInspectionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<InProcessInspectionSummaryDto> fetchAllSummaries(InProcessInspectionSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inProcessInspection.deletedAt))
                .and(contains(inspection.inspectionNo, dto.getInspectionNo()))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(contains(lot.no, dto.getLotNo()))
                .and(startDateGoe(inspection.date, dto.getStartDate()))
                .and(endDateLoe(inspection.date, dto.getEndDate()));

        List<InProcessInspectionSummaryDto> content = query
                .select(new QInProcessInspectionSummaryDto(
                        inProcessInspection.id,
                        inspection.inspectionNo,
                        inspection.date,
                        workOrder.date,
                        productionOrderProcess.processName,
                        item.code,
                        item.name,
                        lot.no,
                        inspection.inspectedQuantity,
                        uom.code,
                        uom.scale,
                        inspection.status,
                        inspection.judgement
                ))
                .from(inProcessInspection)
                .innerJoin(workResult).on(inProcessInspection.workResult.id.eq(workResult.id))
                .innerJoin(inspection).on(inProcessInspection.inspection.id.eq(inspection.id))
                .innerJoin(uom).on(inspection.uom.id.eq(uom.id))
                .innerJoin(workOrder).on(workResult.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(inspection.inspectionNo.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inProcessInspection.count())
                .from(inProcessInspection)
                .innerJoin(workResult).on(inProcessInspection.workResult.id.eq(workResult.id))
                .innerJoin(inspection).on(inProcessInspection.inspection.id.eq(inspection.id))
                .innerJoin(uom).on(inspection.uom.id.eq(uom.id))
                .innerJoin(workOrder).on(workResult.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .leftJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

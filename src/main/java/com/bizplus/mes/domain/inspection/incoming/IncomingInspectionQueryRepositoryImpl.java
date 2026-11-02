package com.bizplus.mes.domain.inspection.incoming;

import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSearchDto;
import com.bizplus.mes.domain.inspection.incoming.dto.IncomingInspectionSummaryDto;
import com.bizplus.mes.domain.inspection.incoming.dto.QIncomingInspectionSummaryDto;
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
import static com.bizplus.mes.domain.inspection.incoming.QIncomingInspection.incomingInspection;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.receipt.QReceipt.receipt;
import static com.bizplus.mes.domain.receipt.item.QReceiptItem.receiptItem;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IncomingInspectionQueryRepositoryImpl implements IncomingInspectionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<IncomingInspectionSummaryDto> fetchAll(IncomingInspectionSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(incomingInspection.deletedAt))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(contains(lot.no, dto.getLotNo()))
                .and(startDateGoe(inspection.date, dto.getStartDate()))
                .and(endDateLoe(inspection.date, dto.getEndDate()));

        List<IncomingInspectionSummaryDto> content = query.select(new QIncomingInspectionSummaryDto(
                        incomingInspection.id,
                        inspection.inspectionNo,
                        inspection.date,
                        receipt.receiptNo,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        lot.no,
                        receiptItem.quantity,
                        uom.code,
                        uom.scale,
                        inspection.status,
                        inspection.judgement
                ))
                .from(incomingInspection)
                .innerJoin(inspection).on(incomingInspection.inspection.id.eq(inspection.id))
                .innerJoin(receiptItem).on(incomingInspection.receiptItem.id.eq(receiptItem.id))
                .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                .innerJoin(uom).on(receiptItem.uom.id.eq(uom.id))
                .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(inspection.date.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(incomingInspection.count())
                .from(incomingInspection)
                .innerJoin(inspection).on(incomingInspection.inspection.id.eq(inspection.id))
                .innerJoin(receiptItem).on(incomingInspection.receiptItem.id.eq(receiptItem.id))
                .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                .innerJoin(uom).on(receiptItem.uom.id.eq(uom.id))
                .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

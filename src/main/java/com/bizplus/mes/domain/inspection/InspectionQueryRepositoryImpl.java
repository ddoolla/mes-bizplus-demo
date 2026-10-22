package com.bizplus.mes.domain.inspection;

import com.bizplus.mes.domain.inspection.dto.InspectionDto;
import com.bizplus.mes.domain.inspection.dto.InspectionSearchDto;
import com.bizplus.mes.domain.inspection.dto.QInspectionDto;
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
import static com.bizplus.mes.domain.inspection.spec.QInspectionSpec.inspectionSpec;
import static com.bizplus.mes.domain.user.QUser.user;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InspectionQueryRepositoryImpl implements InspectionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<InspectionDto> fetchInspections(InspectionSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inspection.deletedAt))
                .and(contains(inspection.inspectionNo, dto.getInspectionNo()))
                .and(eq(inspection.type, dto.getType()))
                .and(eq(inspection.status, dto.getStatus()))
                .and(eq(inspection.judgement, dto.getJudgement()))
                .and(startDateGoe(inspection.date, dto.getStartDate()))
                .and(endDateLoe(inspection.date, dto.getEndDate()));

        List<InspectionDto> content = query
                .select(new QInspectionDto(
                        inspection.id,
                        inspection.inspectionNo,
                        inspection.date,
                        inspection.type,
                        inspection.status,
                        inspection.judgement,
                        inspection.remark,
                        inspectionSpec.id,
                        inspectionSpec.code,
                        inspectionSpec.name,
                        user.id,
                        user.name
                ))
                .from(inspection)
                .innerJoin(inspectionSpec).on(inspection.inspectionSpec.id.eq(inspection.id))
                .leftJoin(user).on(inspection.inspector.id.eq(user.id))
                .where(searchCondition)
                .orderBy(inspection.date.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(inspection.count())
                .from(inspection)
                .innerJoin(inspectionSpec).on(inspection.inspectionSpec.id.eq(inspection.id))
                .leftJoin(user).on(inspection.inspector.id.eq(user.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

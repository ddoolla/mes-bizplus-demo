package com.bizplus.mes.domain.inspection.item;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemDto;
import com.bizplus.mes.domain.inspection.item.dto.InspectionItemSearchDto;
import com.bizplus.mes.domain.inspection.item.dto.QInspectionItemDto;
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
import static com.bizplus.mes.domain.inspection.item.QInspectionItem.inspectionItem;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InspectionItemQueryRepositoryImpl implements InspectionItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode groupCode = new QCommonCode("groupCode");

    @Override
    public Page<InspectionItemDto> findInspectionItems(InspectionItemSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inspectionItem.deletedAt))
                .and(contains(inspectionItem.code, dto.getCode()))
                .and(contains(inspectionItem.name, dto.getName()))
                .and(eq(inspectionItem.group.id, dto.getGroupId()));

        List<InspectionItemDto> content = query
                .select(new QInspectionItemDto(
                        inspectionItem.id,
                        inspectionItem.code,
                        inspectionItem.name,
                        inspectionItem.description,
                        groupCode.id,
                        groupCode.code,
                        groupCode.name
                ))
                .from(inspectionItem)
                .leftJoin(groupCode).on(inspectionItem.group.id.eq(groupCode.id))
                .where(searchCondition)
                .orderBy(inspectionItem.code.asc(), inspectionItem.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inspectionItem.count())
                .from(inspectionItem)
                .leftJoin(groupCode).on(inspectionItem.group.id.eq(groupCode.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<InspectionItemDto> findInspectionItem(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QInspectionItemDto(
                                inspectionItem.id,
                                inspectionItem.code,
                                inspectionItem.name,
                                inspectionItem.description,
                                groupCode.id,
                                groupCode.code,
                                groupCode.name
                        ))
                        .from(inspectionItem)
                        .leftJoin(groupCode).on(inspectionItem.group.id.eq(groupCode.id))
                        .where(
                                notDeleted(inspectionItem.deletedAt),
                                eq(inspectionItem.id, id)
                        )
                        .fetchOne()

        );
    }
}

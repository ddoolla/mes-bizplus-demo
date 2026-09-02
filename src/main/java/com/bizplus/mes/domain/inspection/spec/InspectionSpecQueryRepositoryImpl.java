package com.bizplus.mes.domain.inspection.spec;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecDto;
import com.bizplus.mes.domain.inspection.spec.dto.InspectionSpecSearchDto;
import com.bizplus.mes.domain.inspection.spec.dto.QInspectionSpecDto;
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
import static com.bizplus.mes.domain.inspection.spec.QInspectionSpec.inspectionSpec;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.process.QProcess.process;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InspectionSpecQueryRepositoryImpl implements InspectionSpecQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode itemCategoryCode = new QCommonCode("itemCategoryCode");

    @Override
    public Page<InspectionSpecDto> findInspectionSpecs(InspectionSpecSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inspectionSpec.deletedAt))
                .and(contains(inspectionSpec.code, dto.getCode()))
                .and(contains(inspectionSpec.name, dto.getName()))
                .and(eq(inspectionSpec.type, dto.getType()))
                .and(contains(item.code, dto.getItemCode()))
                .and(contains(item.name, dto.getItemName()));

        List<InspectionSpecDto> content = query
                .select(new QInspectionSpecDto(
                        inspectionSpec.id,
                        inspectionSpec.code,
                        inspectionSpec.name,
                        inspectionSpec.type,
                        inspectionSpec.version,
                        inspectionSpec.primary,
                        inspectionSpec.remark,
                        item.id,
                        item.code,
                        item.name,
                        itemCategoryCode.name,
                        item.type,
                        process.id,
                        process.code,
                        process.name
                ))
                .from(inspectionSpec)
                .innerJoin(item).on(inspectionSpec.item.id.eq(item.id))
                .leftJoin(itemCategoryCode).on(item.category.id.eq(itemCategoryCode.id))
                .leftJoin(process).on(inspectionSpec.process.id.eq(process.id))
                .where(searchCondition)
                .orderBy(inspectionSpec.code.asc(), inspectionSpec.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inspectionSpec.count())
                .from(inspectionSpec)
                .innerJoin(item).on(inspectionSpec.item.id.eq(item.id))
                .leftJoin(itemCategoryCode).on(item.category.id.eq(itemCategoryCode.id))
                .leftJoin(process).on(inspectionSpec.process.id.eq(process.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<InspectionSpecDto> findInspectionSpec(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QInspectionSpecDto(
                                inspectionSpec.id,
                                inspectionSpec.code,
                                inspectionSpec.name,
                                inspectionSpec.type,
                                inspectionSpec.version,
                                inspectionSpec.primary,
                                inspectionSpec.remark,
                                item.id,
                                item.code,
                                item.name,
                                itemCategoryCode.name,
                                item.type,
                                process.id,
                                process.code,
                                process.name
                        ))
                        .from(inspectionSpec)
                        .innerJoin(item).on(inspectionSpec.item.id.eq(item.id))
                        .leftJoin(itemCategoryCode).on(item.category.id.eq(itemCategoryCode.id))
                        .leftJoin(process).on(inspectionSpec.process.id.eq(process.id))
                        .where(
                                notDeleted(inspectionSpec.deletedAt),
                                eq(inspectionSpec.id, id)
                        )
                        .fetchOne()
        );
    }

    @Transactional
    @Override
    public void resetPrimary(Long itemId, Long processId, InspectionType type) {
        query
                .update(inspectionSpec)
                .set(inspectionSpec.primary, false)
                .where(
                        inspectionSpec.primary.isTrue(),
                        eq(inspectionSpec.item.id, itemId),
                        eq(inspectionSpec.process.id, processId),
                        eq(inspectionSpec.type, type)
                )
                .execute();
    }
}

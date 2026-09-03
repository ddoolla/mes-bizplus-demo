package com.bizplus.mes.domain.defect.item;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.defect.item.dto.DefectItemDto;
import com.bizplus.mes.domain.defect.item.dto.DefectItemSearchDto;
import com.bizplus.mes.domain.defect.item.dto.QDefectItemDto;
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
import static com.bizplus.mes.domain.defect.item.QDefectItem.defectItem;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DefectItemQueryRepositoryImpl implements DefectItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode defectTypeCode = new QCommonCode("defectTypeCode");

    @Override
    public Page<DefectItemDto> findDefectItems(DefectItemSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(defectItem.deletedAt))
                .and(contains(defectItem.code, dto.getCode()))
                .and(contains(defectItem.name, dto.getName()))
                .and(eq(defectItem.type.id, dto.getTypeId()));

        List<DefectItemDto> content = query
                .select(new QDefectItemDto(
                        defectItem.id,
                        defectItem.code,
                        defectItem.name,
                        defectItem.description,
                        defectItem.remark,
                        defectTypeCode.id,
                        defectTypeCode.code,
                        defectTypeCode.name
                ))
                .from(defectItem)
                .leftJoin(defectTypeCode).on(defectItem.type.id.eq(defectTypeCode.id))
                .where(searchCondition)
                .orderBy(defectItem.code.asc(), defectItem.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(defectItem.count())
                .from(defectItem)
                .leftJoin(defectTypeCode).on(defectItem.type.id.eq(defectTypeCode.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<DefectItemDto> findDefectItem(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QDefectItemDto(
                                defectItem.id,
                                defectItem.code,
                                defectItem.name,
                                defectItem.description,
                                defectItem.remark,
                                defectTypeCode.id,
                                defectTypeCode.code,
                                defectTypeCode.name
                        ))
                        .from(defectItem)
                        .leftJoin(defectTypeCode).on(defectItem.type.id.eq(defectTypeCode.id))
                        .where(
                                notDeleted(defectItem.deletedAt),
                                eq(defectItem.id, id)
                        )
                        .fetchOne()

        );
    }
}

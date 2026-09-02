package com.bizplus.mes.domain.inspection.spec.item;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inspection.spec.item.dto.InspectionSpecItemDto;
import com.bizplus.mes.domain.inspection.spec.item.dto.QInspectionSpecItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.inspection.item.QInspectionItem.inspectionItem;
import static com.bizplus.mes.domain.inspection.spec.item.QInspectionSpecItem.inspectionSpecItem;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InspectionSpecItemQueryRepositoryImpl implements InspectionSpecItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode inspectionGroupCode = new QCommonCode("inspectionGroupCode");

    @Override
    public List<InspectionSpecItemDto> findInspectionSpecItems(Long inspectionSpecId) {
        return query
                .select(new QInspectionSpecItemDto(
                        inspectionSpecItem.id,
                        inspectionSpecItem.sortOrder,
                        inspectionSpecItem.standard,
                        inspectionSpecItem.method,
                        inspectionSpecItem.remark,
                        inspectionItem.id,
                        inspectionGroupCode.name,
                        inspectionItem.code,
                        inspectionItem.name,
                        inspectionItem.description
                ))
                .from(inspectionSpecItem)
                .innerJoin(inspectionItem).on(inspectionSpecItem.inspectionItem.id.eq(inspectionItem.id))
                .leftJoin(inspectionGroupCode).on(inspectionItem.group.id.eq(inspectionGroupCode.id))
                .where(
                        notDeleted(inspectionSpecItem.deletedAt),
                        eq(inspectionSpecItem.inspectionSpec.id, inspectionSpecId)
                )
                .orderBy(inspectionSpecItem.sortOrder.asc())
                .fetch();
    }

    @Override
    public Integer findNextSortOrder(Long inspectionSpecId) {
        return query
                .select(
                        inspectionSpecItem.sortOrder
                                .max()
                                .coalesce(0)
                                .add(1)
                )
                .from(inspectionSpecItem)
                .where(
                        notDeleted(inspectionSpecItem.deletedAt),
                        eq(inspectionSpecItem.inspectionSpec.id, inspectionSpecId)
                )
                .fetchFirst();
    }
}

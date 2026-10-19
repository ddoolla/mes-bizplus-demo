package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.MaterialInputSummaryDto;
import com.bizplus.mes.domain.material.input.dto.QMaterialInputSummaryDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.material.input.QMaterialInput.materialInput;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MaterialInputQueryRepositoryImpl implements MaterialInputQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<MaterialInputSummaryDto> fetchMaterialInputSummaries(Long workOrderId) {
        return query
                .select(new QMaterialInputSummaryDto(
                        item.id,
                        item.code,
                        item.name,
                        item.specification,
                        item.lotManaged,
                        uom.code,
                        uom.scale,
                        materialInput.quantity.sum().coalesce(BigDecimal.ZERO)
                ))
                .from(materialInput)
                .innerJoin(item).on(materialInput.item.id.eq(item.id))
                .innerJoin(uom).on(materialInput.uom.id.eq(uom.id))
                .where(
                        eq(materialInput.workOrder.id, workOrderId)
                )
                .groupBy(materialInput.item.id)
                .fetch();
    }
}

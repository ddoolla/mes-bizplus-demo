package com.bizplus.mes.domain.process.material;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.process.material.dto.ProcessMaterialDto;
import com.bizplus.mes.domain.process.material.dto.QProcessMaterialDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.process.material.QProcessMaterial.processMaterial;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProcessMaterialQueryRepositoryImpl implements ProcessMaterialQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public List<ProcessMaterialDto> findProcessMaterials(Long routingProcessId) {
        return query
                .select(new QProcessMaterialDto(
                        processMaterial.id,
                        processMaterial.quantity,
                        processMaterial.consumptionMethod,
                        item.id,
                        item.code,
                        item.name,
                        categoryCode.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.name,
                        uom.type,
                        uom.scale
                ))
                .from(processMaterial)
                .innerJoin(item).on(processMaterial.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(processMaterial.uom.id.eq(uom.id))
                .where(
                        eq(processMaterial.routingProcess.id, routingProcessId)
                )
                .orderBy(item.code.asc(), item.name.asc())
                .fetch();
    }
}

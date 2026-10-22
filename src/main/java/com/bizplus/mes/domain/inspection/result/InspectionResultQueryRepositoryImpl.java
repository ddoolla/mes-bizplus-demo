package com.bizplus.mes.domain.inspection.result;

import com.bizplus.mes.domain.inspection.result.dto.InspectionResultDto;
import com.bizplus.mes.domain.inspection.result.dto.QInspectionResultDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.inspection.result.QInspectionResult.inspectionResult;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InspectionResultQueryRepositoryImpl implements InspectionResultQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<InspectionResultDto> fetchInspectionResults(Long inspectionId) {
        return query
                .select(new QInspectionResultDto(
                        inspectionResult.id,
                        inspectionResult.inspectionSpecItem.id,
                        inspectionResult.itemId,
                        inspectionResult.itemGroup,
                        inspectionResult.itemCode,
                        inspectionResult.itemName,
                        inspectionResult.standard,
                        inspectionResult.method,
                        inspectionResult.specRemark,
                        inspectionResult.sortOrder,
                        inspectionResult.measuredValue,
                        inspectionResult.judgement,
                        inspectionResult.remark
                ))
                .from(inspectionResult)
                .where(
                        notDeleted(inspectionResult.deletedAt),
                        eq(inspectionResult.inspection.id, inspectionId)
                )
                .orderBy(inspectionResult.sortOrder.asc())
                .fetch();
    }
}

package com.bizplus.mes.domain.material.input;

import com.bizplus.mes.domain.material.input.dto.*;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.material.input.QMaterialInput.materialInput;
import static com.bizplus.mes.domain.production.order.QProductionOrder.productionOrder;
import static com.bizplus.mes.domain.production.order.process.QProductionOrderProcess.productionOrderProcess;
import static com.bizplus.mes.domain.production.result.QProductionResult.productionResult;
import static com.bizplus.mes.domain.uom.QUom.uom;
import static com.bizplus.mes.domain.work.order.QWorkOrder.workOrder;

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
                        item.type,
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
                        notDeleted(materialInput.deletedAt),
                        eq(materialInput.workOrder.id, workOrderId)
                )
                .groupBy(materialInput.item.id)
                .fetch();
    }

    @Override
    public List<MaterialInputDto> fetchMaterialInput(Long workOrderId) {
        return query
                .select(new QMaterialInputDto(
                        materialInput.id,
                        materialInput.type,
                        materialInput.quantity,
                        materialInput.inputAt,
                        workOrder.id,
                        workOrder.orderNo,
                        productionOrderProcess.processName,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(materialInput)
                .innerJoin(workOrder).on(materialInput.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(item).on(materialInput.item.id.eq(item.id))
                .innerJoin(uom).on(materialInput.uom.id.eq(uom.id))
                .leftJoin(lot).on(materialInput.lot.id.eq(lot.id))
                .where(
                        notDeleted(materialInput.deletedAt),
                        eq(materialInput.workOrder.id, workOrderId)
                )
                .orderBy(materialInput.inputAt.desc())
                .fetch();
    }

    @Override
    public List<MaterialInputDto> fetchAllByLotId(Long lotId) {
        return query
                .select(new QMaterialInputDto(
                        materialInput.id,
                        materialInput.type,
                        materialInput.quantity,
                        materialInput.inputAt,
                        workOrder.id,
                        workOrder.orderNo,
                        productionOrderProcess.processName,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(materialInput)
                .innerJoin(workOrder).on(materialInput.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(item).on(materialInput.item.id.eq(item.id))
                .innerJoin(uom).on(materialInput.uom.id.eq(uom.id))
                .innerJoin(lot).on(materialInput.lot.id.eq(lot.id))
                .where(
                        notDeleted(materialInput.deletedAt),
                        eq(materialInput.lot.id, lotId)
                )
                .orderBy(materialInput.inputAt.desc())
                .fetch();
    }

    @Override
    public List<MaterialInputDto> fetchAllByProductionLotId(Long productionLotId) {
        return query
                .select(new QMaterialInputDto(
                        materialInput.id,
                        materialInput.type,
                        materialInput.quantity,
                        materialInput.inputAt,
                        workOrder.id,
                        workOrder.orderNo,
                        productionOrderProcess.processName,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(materialInput)
                .innerJoin(workOrder).on(materialInput.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(materialInput.item.id.eq(item.id))
                .innerJoin(uom).on(materialInput.uom.id.eq(uom.id))
                .innerJoin(lot).on(materialInput.lot.id.eq(lot.id))
                .where(
                        notDeleted(materialInput.deletedAt),
                        eq(productionOrder.lot.id, productionLotId)
                )
                .orderBy(materialInput.inputAt.desc())
                .fetch();
    }

    @Override
    public List<MaterialInputProductionLotDto> fetchProductionLots(Long inputLotId) {
        return query.select(new QMaterialInputProductionLotDto(
                        lot.id,
                        lot.no,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.code,
                        uom.scale,
                        productionResult.goodQuantity
                ))
                .from(materialInput)
                .innerJoin(workOrder).on(materialInput.workOrder.id.eq(workOrder.id))
                .innerJoin(productionOrderProcess).on(workOrder.productionOrderProcess.id.eq(productionOrderProcess.id))
                .innerJoin(productionOrder).on(productionOrderProcess.productionOrder.id.eq(productionOrder.id))
                .leftJoin(productionResult).on(productionResult.productionOrder.id.eq(productionOrder.id))
                .innerJoin(item).on(productionOrder.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .innerJoin(lot).on(productionOrder.lot.id.eq(lot.id))
                .where(
                        notDeleted(materialInput.deletedAt),
                        eq(materialInput.lot.id, inputLotId)
                )
                .orderBy(materialInput.inputAt.desc())
                .fetch();
    }
}

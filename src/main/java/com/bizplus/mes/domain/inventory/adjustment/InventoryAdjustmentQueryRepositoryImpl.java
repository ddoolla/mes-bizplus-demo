package com.bizplus.mes.domain.inventory.adjustment;

import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.InventoryAdjustmentSearchDto;
import com.bizplus.mes.domain.inventory.adjustment.dto.QInventoryAdjustmentDto;
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
import static com.bizplus.mes.domain.inventory.adjustment.QInventoryAdjustment.inventoryAdjustment;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryAdjustmentQueryRepositoryImpl implements InventoryAdjustmentQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<InventoryAdjustmentDto> fetchAll(InventoryAdjustmentSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inventoryAdjustment.deletedAt))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(contains(lot.no, dto.getLotNo()))
                .and(startDateGoe(inventoryAdjustment.date, dto.getStartDate()))
                .and(endDateLoe(inventoryAdjustment.date, dto.getEndDate()));

        List<InventoryAdjustmentDto> content = query
                .select(new QInventoryAdjustmentDto(
                        inventoryAdjustment.id,
                        inventoryAdjustment.date,
                        inventoryAdjustment.beforeQuantity,
                        inventoryAdjustment.afterQuantity,
                        inventoryAdjustment.reason,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(inventoryAdjustment)
                .innerJoin(item).on(inventoryAdjustment.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(inventoryAdjustment.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(inventoryAdjustment.date.desc(), inventoryAdjustment.createdAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inventoryAdjustment.count())
                .from(inventoryAdjustment)
                .innerJoin(item).on(inventoryAdjustment.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(inventoryAdjustment.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

}

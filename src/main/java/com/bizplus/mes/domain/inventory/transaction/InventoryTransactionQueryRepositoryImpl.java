package com.bizplus.mes.domain.inventory.transaction;

import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionDto;
import com.bizplus.mes.domain.inventory.transaction.dto.InventoryTransactionSearchDto;
import com.bizplus.mes.domain.inventory.transaction.dto.QInventoryTransactionDto;
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
import static com.bizplus.mes.domain.inventory.transaction.QInventoryTransaction.inventoryTransaction;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryTransactionQueryRepositoryImpl implements InventoryTransactionQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<InventoryTransactionDto> fetchAll(InventoryTransactionSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(contains(lot.no, dto.getLotNo()))
                .and(eq(inventoryTransaction.type, dto.getType()))
                .and(startDateGoe(inventoryTransaction.transactionAt, dto.getStartDate()))
                .and(endDateLoe(inventoryTransaction.transactionAt, dto.getEndDate()));

        List<InventoryTransactionDto> content = query
                .select(new QInventoryTransactionDto(
                        inventoryTransaction.id,
                        inventoryTransaction.type,
                        inventoryTransaction.quantity,
                        inventoryTransaction.transactionAt,
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
                .from(inventoryTransaction)
                .innerJoin(item).on(inventoryTransaction.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(inventoryTransaction.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(inventoryTransaction.transactionAt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inventoryTransaction.count())
                .from(inventoryTransaction)
                .innerJoin(item).on(inventoryTransaction.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(inventoryTransaction.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public List<InventoryTransactionDto> fetchAllByLotId(Long lotId) {
        return query
                .select(new QInventoryTransactionDto(
                        inventoryTransaction.id,
                        inventoryTransaction.type,
                        inventoryTransaction.quantity,
                        inventoryTransaction.transactionAt,
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
                .from(inventoryTransaction)
                .innerJoin(item).on(inventoryTransaction.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(inventoryTransaction.lot.id.eq(lot.id))
                .where(
                        eq(inventoryTransaction.lot.id, lotId)
                )
                .orderBy(inventoryTransaction.transactionAt.desc())
                .fetch();
    }
}

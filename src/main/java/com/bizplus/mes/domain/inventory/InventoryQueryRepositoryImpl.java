package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.InventorySummaryDto;
import com.bizplus.mes.domain.inventory.dto.QInventorySummaryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryQueryRepositoryImpl implements InventoryQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public Page<InventorySummaryDto> findInventorySummaries(InventorySearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(inventory.deletedAt))
                .and(contains(item.code, dto.getItemCode()))
                .and(contains(item.name, dto.getItemName()))
                .and(eq(item.type, dto.getItemType()))
                .and(eq(item.category.id, dto.getCategoryCodeId()));

        List<InventorySummaryDto> content = query
                .select(new QInventorySummaryDto(
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
                        uom.scale,
                        inventory.quantity.sum(),
                        inventory.reservedQuantity.sum()
                ))
                .from(inventory)
                .innerJoin(item).on(inventory.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .groupBy(item.id)
                .orderBy(item.code.asc(), item.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(item.id.countDistinct())
                .from(inventory)
                .innerJoin(item).on(inventory.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<Inventory> findInventory(Long itemId, Long lotId) {
        return Optional.ofNullable(
                query
                        .selectFrom(inventory)
                        .where(
                                notDeleted(inventory.deletedAt),
                                eq(inventory.item.id, itemId),
                                lotIdEq(lotId)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<Inventory> findInventoryForUpdate(Long itemId, Long lotId) {
        return Optional.ofNullable(
                query
                        .selectFrom(inventory)
                        .where(
                                eq(inventory.item.id, itemId),
                                lotIdEq(lotId)
                        )
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE) // 비관적 쓰기 잠금 조회
                        .fetchOne()
        );
    }

    private BooleanExpression lotIdEq(Long lotId) {
        return lotId == null
                ? inventory.lot.isNull()
                : inventory.lot.id.eq(lotId);
    }

    @Override
    public boolean existsStockByItemId(Long itemId) {
        return query
                .selectOne()
                .from(inventory)
                .where(
                        eq(inventory.item.id, itemId),
                        inventory.quantity.gt(BigDecimal.ZERO)
                )
                .fetchFirst() != null;
    }

    @Override
    public List<Inventory> findAvailableInventories(Long itemId) {
        return query
                .selectFrom(inventory)
                .innerJoin(inventory.item, item).fetchJoin()
                .leftJoin(inventory.lot, lot).fetchJoin()
                .where(
                        eq(inventory.item.id, itemId)
                )
                .orderBy(inventory.createdAt.asc())
                .fetch();
    }
}

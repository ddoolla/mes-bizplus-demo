package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inventory.dto.InventoryDto;
import com.bizplus.mes.domain.inventory.dto.InventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.QInventoryDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryQueryRepositoryImpl implements InventoryQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public Page<InventoryDto> findInventoriesGroupByItem(InventorySearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(item.deletedAt))
                .and(contains(item.code, dto.getItemCode()))
                .and(contains(item.name, dto.getItemName()))
                .and(eq(item.type, dto.getItemType()))
                .and(eq(item.category.id, dto.getCategoryCodeId()));

        List<InventoryDto> content = query
                .select(new QInventoryDto(
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
}

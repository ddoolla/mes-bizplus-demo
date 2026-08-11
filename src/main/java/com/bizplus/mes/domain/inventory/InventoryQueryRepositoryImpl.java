package com.bizplus.mes.domain.inventory;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.inventory.dto.ItemInventoryDto;
import com.bizplus.mes.domain.inventory.dto.ItemInventorySearchDto;
import com.bizplus.mes.domain.inventory.dto.QItemInventoryDto;
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
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryQueryRepositoryImpl implements InventoryQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public Page<ItemInventoryDto> findInventories(ItemInventorySearchDto dto, Pageable pageable) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(item.deletedAt))
                .and(contains(item.code, dto.getCode()))
                .and(contains(item.code, dto.getName()))
                .and(eq(item.type, dto.getType()))
                .and(eq(item.category.id, dto.getCategoryId()));

        List<ItemInventoryDto> content = query
                .select(new QItemInventoryDto(
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
                        uom.decimalPlaces,
                        inventory.id,
                        inventory.quantity,
                        inventory.reservedQuantity
                ))
                .from(inventory)
                .innerJoin(item).on(inventory.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .orderBy(item.code.asc(), item.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(inventory.count())
                .from(inventory)
                .innerJoin(item).on(inventory.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

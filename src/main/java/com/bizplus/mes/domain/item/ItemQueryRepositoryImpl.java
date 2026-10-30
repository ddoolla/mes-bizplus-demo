package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import com.bizplus.mes.domain.item.dto.QItemDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPAExpressions;
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
import static com.bizplus.mes.domain.bom.QBom.bom;
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.routing.QRouting.routing;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public Page<ItemDto> fetchItems(ItemSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(item.deletedAt))
                .and(contains(item.code, dto.getCode()))
                .and(contains(item.name, dto.getName()))
                .and(eq(item.type, dto.getType()))
                .and(eq(categoryCode.id, dto.getCategoryId()));

        if (dto.getGroup() != null) {
            searchCondition.and(item.type.in(dto.getGroup().getTypes()));
        }

        List<ItemDto> content = query
                .select(new QItemDto(
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        item.unitPrice,
                        item.lotManaged,
                        item.shelfLife,
                        item.shelfLifeUnit,
                        item.remark,
                        JPAExpressions
                                .select(inventory.quantity.sum().coalesce(BigDecimal.ZERO))
                                .from(inventory)
                                .where(inventory.item.id.eq(item.id)),
                        categoryCode.id,
                        categoryCode.name,
                        uom.id,
                        uom.code,
                        uom.scale,
                        bom.id,
                        bom.code,
                        bom.name,
                        routing.id,
                        routing.code,
                        routing.name
                ))
                .from(item)
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(bom).on(item.defaultBom.id.eq(bom.id))
                .leftJoin(routing).on(item.defaultRouting.id.eq(routing.id))
                .where(searchCondition)
                .orderBy(item.code.asc(), item.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(item.count())
                .from(item)
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(bom).on(item.defaultBom.id.eq(bom.id))
                .leftJoin(routing).on(item.defaultRouting.id.eq(routing.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<ItemDto> fetchItem(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QItemDto(
                                item.id,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                item.unitPrice,
                                item.lotManaged,
                                item.shelfLife,
                                item.shelfLifeUnit,
                                item.remark,
                                JPAExpressions
                                        .select(inventory.quantity.sum().coalesce(BigDecimal.ZERO))
                                        .from(inventory)
                                        .where(inventory.item.id.eq(item.id)),
                                categoryCode.id,
                                categoryCode.name,
                                uom.id,
                                uom.code,
                                uom.scale,
                                bom.id,
                                bom.code,
                                bom.name,
                                routing.id,
                                routing.code,
                                routing.name
                        ))
                        .from(item)
                        .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                        .innerJoin(uom).on(item.uom.id.eq(uom.id))
                        .leftJoin(bom).on(item.defaultBom.id.eq(bom.id))
                        .leftJoin(routing).on(item.defaultRouting.id.eq(routing.id))
                        .where(
                                notDeleted(item.deletedAt),
                                eq(item.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<Item> findItemForUpdate(Long id) {
        return Optional.ofNullable(
                query
                        .selectFrom(item)
                        .where(
                                notDeleted(item.deletedAt),
                                eq(item.id, id)
                        )
                        .setLockMode(LockModeType.PESSIMISTIC_WRITE) // 비관적 쓰기 잠금 조회
                        .fetchOne()
        );
    }
}

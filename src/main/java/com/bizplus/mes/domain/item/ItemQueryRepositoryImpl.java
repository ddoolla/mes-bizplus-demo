package com.bizplus.mes.domain.item;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.item.dto.ItemDto;
import com.bizplus.mes.domain.item.dto.ItemSearchDto;
import com.bizplus.mes.domain.item.dto.QItemDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemQueryRepositoryImpl implements ItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    private Page<ItemDto> findItems(ItemSearchDto dto, ItemGroup group, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(item.deletedAt))
                .and(contains(item.code, dto.getCode()))
                .and(contains(item.name, dto.getName()))
                .and(eq(item.type, dto.getType()))
                .and(eq(categoryCode.id, dto.getCategoryId()));

        if (group != null) {
            searchCondition.and(item.type.in(group.getTypes()));
        }

        List<ItemDto> content = query
                .select(new QItemDto(
                        item.id,
                        categoryCode.id,
                        categoryCode.name,
                        uom.id,
                        uom.code,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        item.remark,
                        item.lotManaged
                ))
                .from(item)
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition)
                .orderBy(item.code.asc(), item.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(item.count())
                .from(item)
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Page<ItemDto> findItems(ItemSearchDto dto, Pageable pageable) {
        return findItems(dto, null, pageable);
    }

    @Override
    public Page<ItemDto> findProducts(ItemSearchDto dto, Pageable pageable) {
        return findItems(dto, ItemGroup.PRODUCT, pageable);
    }

    @Override
    public Page<ItemDto> findMaterials(ItemSearchDto dto, Pageable pageable) {
        return findItems(dto, ItemGroup.MATERIAL, pageable);
    }

    @Override
    public Optional<ItemDto> findItem(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QItemDto(
                                item.id,
                                categoryCode.id,
                                categoryCode.name,
                                uom.id,
                                uom.code,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                item.remark,
                                item.lotManaged
                        ))
                        .from(item)
                        .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                        .innerJoin(uom).on(item.uom.id.eq(uom.id))
                        .where(
                                notDeleted(item.deletedAt),
                                eq(item.id, id)
                        )
                        .fetchOne()
        );
    }
}

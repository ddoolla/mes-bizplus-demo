package com.bizplus.mes.domain.bom;

import com.bizplus.mes.domain.bom.dto.BomDto;
import com.bizplus.mes.domain.bom.dto.BomSearchDto;
import com.bizplus.mes.domain.bom.dto.QBomDto;
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
import static com.bizplus.mes.domain.bom.QBom.bom;
import static com.bizplus.mes.domain.item.QItem.item;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BomQueryRepositoryImpl implements BomQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<BomDto> findBoms(BomSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(bom.deletedAt))
                .and(contains(item.code, dto.getItemCode()))
                .and(contains(item.name, dto.getItemName()))
                .and(contains(bom.code, dto.getCode()))
                .and(contains(bom.name, dto.getName()));

        List<BomDto> content = query
                .select(new QBomDto(
                        bom.id,
                        bom.code,
                        bom.name,
                        bom.version,
                        bom.primary,
                        bom.remark,
                        item.id,
                        item.code,
                        item.name
                ))
                .from(bom)
                .innerJoin(item).on(bom.item.id.eq(item.id))
                .where(searchCondition)
                .orderBy(bom.code.asc(), bom.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(bom.count())
                .from(bom)
                .innerJoin(item).on(bom.item.id.eq(item.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public List<BomDto> findBoms(Long itemId) {
        return query
                .select(new QBomDto(
                        bom.id,
                        bom.code,
                        bom.name,
                        bom.version,
                        bom.primary,
                        bom.remark,
                        item.id,
                        item.code,
                        item.name
                ))
                .from(bom)
                .innerJoin(item).on(bom.item.id.eq(item.id))
                .where(
                        notDeleted(bom.deletedAt),
                        eq(bom.item.id, itemId)
                )
                .fetch();
    }

    @Override
    public Optional<BomDto> findBom(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QBomDto(
                                bom.id,
                                bom.code,
                                bom.name,
                                bom.version,
                                bom.primary,
                                bom.remark,
                                item.id,
                                item.code,
                                item.name
                        ))
                        .from(bom)
                        .innerJoin(item).on(bom.item.id.eq(item.id))
                        .where(
                                notDeleted(bom.deletedAt),
                                eq(bom.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<BomDto> findPrimaryBom(Long itemId) {
        return Optional.ofNullable(
                query
                        .select(new QBomDto(
                                bom.id,
                                bom.code,
                                bom.name,
                                bom.version,
                                bom.primary,
                                bom.remark,
                                item.id,
                                item.code,
                                item.name
                        ))
                        .from(bom)
                        .innerJoin(item).on(bom.item.id.eq(item.id))
                        .where(
                                notDeleted(bom.deletedAt),
                                eq(bom.item.id, itemId),
                                eq(bom.primary, true)
                        )
                        .fetchOne()
        );
    }

    @Transactional
    @Override
    public void resetPrimary(Long itemId) {
        query
                .update(bom)
                .set(bom.primary, false)
                .where(
                        eq(bom.item.id, itemId),
                        eq(bom.primary, true)
                )
                .execute();
    }
}

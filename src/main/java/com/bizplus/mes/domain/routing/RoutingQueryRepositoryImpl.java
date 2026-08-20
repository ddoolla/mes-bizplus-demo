package com.bizplus.mes.domain.routing;

import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.routing.dto.QRoutingDto;
import com.bizplus.mes.domain.routing.dto.RoutingDto;
import com.bizplus.mes.domain.routing.dto.RoutingSearchDto;
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
import static com.bizplus.mes.domain.routing.QRouting.routing;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoutingQueryRepositoryImpl implements RoutingQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");

    @Override
    public Page<RoutingDto> findRoutings(RoutingSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(routing.deletedAt))
                .and(contains(item.code, dto.getItemCode()))
                .and(contains(item.name, dto.getItemName()))
                .and(contains(routing.code, dto.getCode()))
                .and(contains(routing.name, dto.getName()));

        List<RoutingDto> content = query
                .select(new QRoutingDto(
                        routing.id,
                        routing.code,
                        routing.name,
                        routing.version,
                        routing.primary,
                        routing.description,
                        item.id,
                        item.code,
                        item.name,
                        categoryCode.name,
                        item.type
                ))
                .from(routing)
                .innerJoin(item).on(routing.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .where(searchCondition)
                .orderBy(routing.code.asc(), routing.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(routing.count())
                .from(routing)
                .innerJoin(item).on(routing.item.id.eq(item.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<RoutingDto> findRouting(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QRoutingDto(
                                routing.id,
                                routing.code,
                                routing.name,
                                routing.version,
                                routing.primary,
                                routing.description,
                                item.id,
                                item.code,
                                item.name,
                                categoryCode.name,
                                item.type
                        ))
                        .from(routing)
                        .innerJoin(item).on(routing.item.id.eq(item.id))
                        .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                        .where(
                                notDeleted(routing.deletedAt),
                                eq(routing.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public boolean existsPrimary(Long itemId) {
        return query
                .selectOne()
                .from(routing)
                .where(
                        notDeleted(routing.deletedAt),
                        eq(routing.item.id, itemId),
                        eq(routing.primary, true)
                )
                .fetchOne() != null;
    }

    @Transactional
    @Override
    public void resetPrimary(Long itemId) {
        query
                .update(routing)
                .set(routing.primary, false)
                .where(
                        eq(routing.item.id, itemId),
                        routing.primary.isTrue()
                )
                .execute();
    }
}

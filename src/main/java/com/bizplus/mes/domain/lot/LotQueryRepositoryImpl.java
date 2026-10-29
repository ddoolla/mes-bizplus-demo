package com.bizplus.mes.domain.lot;

import com.bizplus.mes.domain.lot.dto.LotDto;
import com.bizplus.mes.domain.lot.dto.LotSearchDto;
import com.bizplus.mes.domain.lot.dto.QLotDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LotQueryRepositoryImpl implements LotQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<LotDto> fetchAll(LotSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(lot.deletedAt))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(contains(lot.no, dto.getLotNo()));

        List<LotDto> content = query
                .select(new QLotDto(
                        lot.id,
                        lot.no,
                        lot.manufactureDate,
                        lot.expirationDate,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        inventory.id,
                        inventory.quantity
                ))
                .from(lot)
                .innerJoin(item).on(lot.item.id.eq(item.id))
                .innerJoin(inventory).on(inventory.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(lot.no.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(lot.count())
                .from(lot)
                .innerJoin(item).on(lot.item.id.eq(item.id))
                .innerJoin(inventory).on(inventory.lot.id.eq(lot.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<LotDto> fetchById(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QLotDto(
                                lot.id,
                                lot.no,
                                lot.manufactureDate,
                                lot.expirationDate,
                                item.id,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                inventory.id,
                                inventory.quantity
                        ))
                        .from(lot)
                        .innerJoin(item).on(lot.item.id.eq(item.id))
                        .innerJoin(inventory).on(inventory.lot.id.eq(lot.id))
                        .where(
                                notDeleted(lot.deletedAt),
                                eq(lot.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public Optional<LotDto> fetchByLotNo(String lotNo) {
        return Optional.ofNullable(
                query
                        .select(new QLotDto(
                                lot.id,
                                lot.no,
                                lot.manufactureDate,
                                lot.expirationDate,
                                item.id,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                inventory.id,
                                inventory.quantity
                        ))
                        .from(lot)
                        .innerJoin(item).on(lot.item.id.eq(item.id))
                        .innerJoin(inventory).on(inventory.lot.id.eq(lot.id))
                        .where(
                                notDeleted(lot.deletedAt),
                                eq(lot.no, lotNo)
                        )
                        .fetchOne()
        );
    }

    @Override
    public String findMaxLotNo(LocalDate date) {
        return query
                .select(lot.no.max())
                .from(lot)
                .where(
                        startDateGoe(lot.createdAt, date),
                        endDateLoe(lot.createdAt, date)
                )
                .fetchFirst();
    }
}

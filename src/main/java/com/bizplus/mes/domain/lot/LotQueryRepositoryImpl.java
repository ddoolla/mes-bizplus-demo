package com.bizplus.mes.domain.lot;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static com.bizplus.mes.common.util.PredicateUtils.endDateLoe;
import static com.bizplus.mes.common.util.PredicateUtils.startDateGoe;
import static com.bizplus.mes.domain.lot.QLot.lot;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LotQueryRepositoryImpl implements LotQueryRepository {

    private final JPAQueryFactory query;

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

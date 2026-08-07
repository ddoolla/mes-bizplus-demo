package com.bizplus.mes.domain.uom;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.contains;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UomQueryRepositoryImpl implements UomQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<Uom> findUoms(String code, String name) {

        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(uom.deletedAt))
                .and(contains(uom.code, code))
                .and(contains(uom.name, name));

        return query
                .selectFrom(uom)
                .where(searchCondition)
                .orderBy(uom.code.asc())
                .fetch();
    }
}

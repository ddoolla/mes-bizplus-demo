package com.bizplus.mes.domain.production.result;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductionResultQueryRepositoryImpl implements ProductionResultQueryRepository {

    private final JPAQueryFactory query;
}

package com.bizplus.mes.domain.work.result;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class WorkResultQueryRepositoryImpl implements WorkResultQueryRepository {

    private final JPAQueryFactory query;
}

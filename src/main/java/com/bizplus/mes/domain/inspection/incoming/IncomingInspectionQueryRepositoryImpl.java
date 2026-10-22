package com.bizplus.mes.domain.inspection.incoming;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class IncomingInspectionQueryRepositoryImpl implements IncomingInspectionQueryRepository {

    private final JPAQueryFactory query;
}

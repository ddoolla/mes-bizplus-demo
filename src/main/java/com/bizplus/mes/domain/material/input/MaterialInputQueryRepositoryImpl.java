package com.bizplus.mes.domain.material.input;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MaterialInputQueryRepositoryImpl implements MaterialInputQueryRepository {

    private final JPAQueryFactory query;
}

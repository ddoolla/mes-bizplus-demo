package com.bizplus.mes.domain.material.receipt;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MaterialReceiptQueryRepositoryImpl implements MaterialReceiptQueryRepository {

    private final JPAQueryFactory query;
}

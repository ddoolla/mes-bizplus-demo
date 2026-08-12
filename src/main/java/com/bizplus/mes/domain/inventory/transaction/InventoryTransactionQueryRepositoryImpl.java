package com.bizplus.mes.domain.inventory.transaction;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class InventoryTransactionQueryRepositoryImpl implements InventoryTransactionQueryRepository {

    private final JPAQueryFactory query;
}

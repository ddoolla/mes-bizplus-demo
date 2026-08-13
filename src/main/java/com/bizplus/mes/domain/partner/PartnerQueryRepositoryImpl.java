package com.bizplus.mes.domain.partner;

import com.bizplus.mes.domain.partner.dto.PartnerSearchDto;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.partner.QPartner.partner;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PartnerQueryRepositoryImpl implements PartnerQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<Partner> findPartners(PartnerSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(partner.deletedAt))
                .and(contains(partner.code, dto.getCode()))
                .and(contains(partner.name, dto.getName()))
                .and(eq(partner.type, dto.getType()));

        List<Partner> content = query
                .selectFrom(partner)
                .where(searchCondition)
                .orderBy(partner.code.asc(), partner.name.asc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(partner.count())
                .from(partner)
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }
}

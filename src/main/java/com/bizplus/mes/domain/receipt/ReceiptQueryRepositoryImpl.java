package com.bizplus.mes.domain.receipt;

import com.bizplus.mes.domain.receipt.dto.QReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptDto;
import com.bizplus.mes.domain.receipt.dto.ReceiptSearchDto;
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
import static com.bizplus.mes.domain.partner.QPartner.partner;
import static com.bizplus.mes.domain.receipt.QReceipt.receipt;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceiptQueryRepositoryImpl implements ReceiptQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Page<ReceiptDto> fetchReceipts(ReceiptSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(receipt.deletedAt))
                .and(contains(receipt.receiptNo, dto.getReceiptNo()))
                .and(containsAny(dto.getPartner(), partner.code, partner.name))
                .and(startDateGoe(receipt.date, dto.getStartDate()))
                .and(endDateLoe(receipt.date, dto.getEndDate()));

        List<ReceiptDto> content = query
                .select(new QReceiptDto(
                        receipt.id,
                        receipt.receiptNo,
                        receipt.date,
                        receipt.status,
                        receipt.remark,
                        partner.id,
                        partner.name
                ))
                .from(receipt)
                .innerJoin(partner).on(receipt.partner.id.eq(partner.id))
                .where(searchCondition)
                .orderBy(receipt.date.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query
                .select(receipt.count())
                .from(receipt)
                .innerJoin(partner).on(receipt.partner.id.eq(partner.id))
                .where(searchCondition);

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public Optional<ReceiptDto> fetchReceipt(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QReceiptDto(
                                receipt.id,
                                receipt.receiptNo,
                                receipt.date,
                                receipt.status,
                                receipt.remark,
                                partner.id,
                                partner.name
                        ))
                        .from(receipt)
                        .innerJoin(partner).on(receipt.partner.id.eq(partner.id))
                        .where(
                                notDeleted(receipt.deletedAt),
                                eq(receipt.id, id)
                        )
                        .fetchOne()
        );
    }

    @Override
    public String findMaxReceiptNo(LocalDate date) {
        return query
                .select(receipt.receiptNo.max())
                .from(receipt)
                .where(
                        startDateGoe(receipt.date, date),
                        endDateLoe(receipt.date, date)
                )
                .fetchFirst();
    }
}

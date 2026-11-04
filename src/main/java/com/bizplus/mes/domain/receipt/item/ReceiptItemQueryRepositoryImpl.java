package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.QReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemSearchDto;
import com.bizplus.mes.domain.uom.QUom;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.receipt.QReceipt.receipt;
import static com.bizplus.mes.domain.receipt.item.QReceiptItem.receiptItem;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ReceiptItemQueryRepositoryImpl implements ReceiptItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QUom itemUom = new QUom("itemUom");
    private static final QUom receiptUom = new QUom("receiptUom");

    @Override
    public Page<ReceiptItemDto> fetchAllForInspection(ReceiptItemSearchDto dto, Pageable pageable) {
        BooleanBuilder searchCondition = new BooleanBuilder()
                .and(notDeleted(receipt.deletedAt))
                .and(notDeleted(receiptItem.deletedAt))
                .and(eq(receiptItem.inspectionRequired, true))
                .and(eq(receiptItem.inspectionAutoCreate, false))
                .and(eq(receiptItem.status, ReceiptItemStatus.PENDING))
                .and(contains(receipt.receiptNo, dto.getReceiptNo()))
                .and(containsAny(dto.getItem(), item.code, item.name))
                .and(startDateGoe(receipt.date, dto.getStartDate()))
                .and(endDateLoe(receipt.date, dto.getEndDate()));

        List<ReceiptItemDto> content = query
                .select(new QReceiptItemDto(
                        receiptItem.id,
                        receiptItem.quantity,
                        receiptItem.unitPrice,
                        receiptItem.amount,
                        receiptItem.inspectionRequired,
                        receiptItem.inspectionAutoCreate,
                        receiptItem.status,
                        receiptItem.remark,
                        receipt.id,
                        receipt.receiptNo,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        itemUom.id,
                        itemUom.code,
                        itemUom.scale,
                        receiptUom.id,
                        receiptUom.code,
                        receiptUom.scale,
                        lot.id,
                        lot.no
                ))
                .from(receiptItem)
                .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                .innerJoin(itemUom).on(item.uom.id.eq(itemUom.id))
                .innerJoin(receiptUom).on(receiptItem.uom.id.eq(receiptUom.id))
                .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id))
                .where(searchCondition)
                .orderBy(receipt.receiptNo.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        JPAQuery<Long> count = query.select(receiptItem.count())
                .from(receiptItem)
                .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                .innerJoin(itemUom).on(item.uom.id.eq(itemUom.id))
                .innerJoin(receiptUom).on(receiptItem.uom.id.eq(receiptUom.id))
                .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    @Override
    public List<ReceiptItemDto> fetchReceiptItems(Long receiptId) {
        return query
                .select(new QReceiptItemDto(
                        receiptItem.id,
                        receiptItem.quantity,
                        receiptItem.unitPrice,
                        receiptItem.amount,
                        receiptItem.inspectionRequired,
                        receiptItem.inspectionAutoCreate,
                        receiptItem.status,
                        receiptItem.remark,
                        receipt.id,
                        receipt.receiptNo,
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        itemUom.id,
                        itemUom.code,
                        itemUom.scale,
                        receiptUom.id,
                        receiptUom.code,
                        receiptUom.scale,
                        lot.id,
                        lot.no
                ))
                .from(receiptItem)
                .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                .innerJoin(itemUom).on(item.uom.id.eq(itemUom.id))
                .innerJoin(receiptUom).on(receiptItem.uom.id.eq(receiptUom.id))
                .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id))
                .where(
                        notDeleted(receiptItem.deletedAt),
                        eq(receiptItem.receipt.id, receiptId)
                )
                .orderBy(item.code.asc(), item.name.asc())
                .fetch();
    }

    @Override
    public List<ReceiptItem> findReceiptItems(Long receiptId) {
        return query
                .selectFrom(receiptItem)
                .innerJoin(receiptItem.item, item).fetchJoin()
                .innerJoin(item.uom, itemUom).fetchJoin()
                .innerJoin(receiptItem.uom, receiptUom).fetchJoin()
                .leftJoin(receiptItem.lot, lot).fetchJoin()
                .where(
                        notDeleted(receiptItem.deletedAt),
                        eq(receiptItem.receipt.id, receiptId)
                )
                .fetch();

    }

    @Override
    public Optional<ReceiptItemDto> fetchById(Long id) {
        return Optional.ofNullable(
                query
                        .select(new QReceiptItemDto(
                                receiptItem.id,
                                receiptItem.quantity,
                                receiptItem.unitPrice,
                                receiptItem.amount,
                                receiptItem.inspectionRequired,
                                receiptItem.inspectionAutoCreate,
                                receiptItem.status,
                                receiptItem.remark,
                                receipt.id,
                                receipt.receiptNo,
                                item.id,
                                item.code,
                                item.name,
                                item.type,
                                item.specification,
                                itemUom.id,
                                itemUom.code,
                                itemUom.scale,
                                receiptUom.id,
                                receiptUom.code,
                                receiptUom.scale,
                                lot.id,
                                lot.no
                        ))
                        .from(receiptItem)
                        .innerJoin(receipt).on(receiptItem.receipt.id.eq(receipt.id))
                        .innerJoin(item).on(receiptItem.item.id.eq(item.id))
                        .innerJoin(itemUom).on(item.uom.id.eq(itemUom.id))
                        .innerJoin(receiptUom).on(receiptItem.uom.id.eq(receiptUom.id))
                        .leftJoin(lot).on(receiptItem.lot.id.eq(lot.id))
                        .where(
                                notDeleted(receiptItem.deletedAt),
                                eq(receiptItem.id, id)
                        )
                        .fetchOne()
        );
    }
}

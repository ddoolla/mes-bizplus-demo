package com.bizplus.mes.domain.receipt.item;

import com.bizplus.mes.domain.receipt.item.dto.QReceiptItemDto;
import com.bizplus.mes.domain.receipt.item.dto.ReceiptItemDto;
import com.bizplus.mes.domain.uom.QUom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
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
    public List<ReceiptItemDto> fetchReceiptItems(Long receiptId) {
        return query
                .select(new QReceiptItemDto(
                        receiptItem.id,
                        receiptItem.quantity,
                        receiptItem.unitPrice,
                        receiptItem.amount,
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

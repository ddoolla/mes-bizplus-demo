package com.bizplus.mes.domain.bom.item;

import com.bizplus.mes.domain.bom.item.dto.BomItemDto;
import com.bizplus.mes.domain.bom.item.dto.QBomItemDto;
import com.bizplus.mes.domain.code.common.QCommonCode;
import com.bizplus.mes.domain.uom.QUom;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.bom.QBom.bom;
import static com.bizplus.mes.domain.bom.item.QBomItem.bomItem;
import static com.bizplus.mes.domain.item.QItem.item;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BomItemQueryRepositoryImpl implements BomItemQueryRepository {

    private final JPAQueryFactory query;

    private static final QCommonCode categoryCode = new QCommonCode("categoryCode");
    private static final QUom itemUom = new QUom("itemUom");
    private static final QUom bomUom = new QUom("bomUom");

    @Override
    public List<BomItemDto> findBomItems(Long bomId) {
        return query
                .select(new QBomItemDto(
                        bomItem.id,
                        item.id,
                        item.code,
                        item.name,
                        categoryCode.name,
                        item.type,
                        item.specification,
                        itemUom.id,
                        itemUom.code,
                        itemUom.scale,
                        bomUom.id,
                        bomUom.code,
                        bomUom.scale,
                        bomItem.quantity
                ))
                .from(bomItem)
                .innerJoin(item).on(bomItem.item.id.eq(item.id))
                .innerJoin(itemUom).on(item.uom.id.eq(itemUom.id))
                .leftJoin(categoryCode).on(item.category.id.eq(categoryCode.id))
                .innerJoin(bomUom).on(bomItem.uom.id.eq(bomUom.id))
                .where(
                        notDeleted(bomItem.deletedAt),
                        eq(bomItem.bom.id, bomId)
                )
                .orderBy(item.code.asc(), item.name.asc())
                .fetch();

    }

    @Override
    public Optional<BomItem> findIncludingDeleted(Long bomId, Long itemId) {
        return Optional.ofNullable(
                query
                        .selectFrom(bomItem)
                        .where(
                                eq(bomItem.bom.id, bomId),
                                eq(bomItem.item.id, itemId)
                        )
                        .fetchOne()
        );
    }

    @Override
    public List<Long> findComponentItemIds(Long parentItemId) {
        return query
                .select(bomItem.item.id)
                .from(bomItem)
                .innerJoin(bom).on(bomItem.bom.id.eq(bom.id))
                .where(
                        eq(bom.item.id, parentItemId),
                        bom.deletedAt.isNull(),
                        bomItem.deletedAt.isNull()
                )
                .distinct()
                .fetch();
    }
}

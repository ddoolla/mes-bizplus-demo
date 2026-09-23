package com.bizplus.mes.domain.sales.order.item;

import com.bizplus.mes.domain.sales.order.item.dto.QSalesOrderItemDto;
import com.bizplus.mes.domain.sales.order.item.dto.SalesOrderItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.common.util.PredicateUtils.notDeleted;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.sales.order.item.QSalesOrderItem.salesOrderItem;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SalesOrderItemQueryRepositoryImpl implements SalesOrderItemQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public List<SalesOrderItemDto> findSalesOrderItems(Long salesOrderId) {
        return query.
                select(new QSalesOrderItemDto(
                        salesOrderItem.id,
                        salesOrderItem.unitPrice,
                        salesOrderItem.quantity,
                        salesOrderItem.supplyAmount,
                        salesOrderItem.taxAmount,
                        salesOrderItem.totalAmount,
                        salesOrderItem.status,
                        salesOrderItem.remark,
                        item.id,
                        item.code,
                        item.name,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale
                ))
                .from(salesOrderItem)
                .innerJoin(item).on(salesOrderItem.item.id.eq(item.id))
                .innerJoin(uom).on(salesOrderItem.uom.id.eq(uom.id))
                .where(
                        notDeleted(salesOrderItem.deletedAt),
                        eq(salesOrderItem.salesOrder.id, salesOrderId)
                )
                .orderBy(salesOrderItem.createdAt.desc())
                .fetch();
    }
}

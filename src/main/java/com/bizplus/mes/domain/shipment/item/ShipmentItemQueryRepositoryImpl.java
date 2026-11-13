package com.bizplus.mes.domain.shipment.item;

import com.bizplus.mes.domain.shipment.dto.ShipmentSearchDto;
import com.bizplus.mes.domain.shipment.item.dto.QShipmentItemDetailDto;
import com.bizplus.mes.domain.shipment.item.dto.QShipmentItemDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDetailDto;
import com.bizplus.mes.domain.shipment.item.dto.ShipmentItemDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static com.bizplus.mes.common.util.PredicateUtils.*;
import static com.bizplus.mes.domain.inventory.QInventory.inventory;
import static com.bizplus.mes.domain.item.QItem.item;
import static com.bizplus.mes.domain.lot.QLot.lot;
import static com.bizplus.mes.domain.partner.QPartner.partner;
import static com.bizplus.mes.domain.shipment.QShipment.shipment;
import static com.bizplus.mes.domain.shipment.item.QShipmentItem.shipmentItem;
import static com.bizplus.mes.domain.uom.QUom.uom;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ShipmentItemQueryRepositoryImpl implements ShipmentItemQueryRepository {

    private final JPAQueryFactory query;

    private final QShipmentItemDetailDto shipmentItemDetailProjection = new QShipmentItemDetailDto(
            shipmentItem.id,
            shipmentItem.quantity,
            shipmentItem.unitPrice,
            shipmentItem.amount,
            inventory.quantity.coalesce(BigDecimal.ZERO),
            shipment.id,
            shipment.shipmentNo,
            shipment.date,
            shipment.status,
            shipment.createdBy,
            partner.id,
            partner.name,
            item.id,
            item.code,
            item.name,
            item.type,
            item.specification,
            uom.id,
            uom.code,
            uom.scale,
            lot.id,
            lot.no
    );

    @Override
    public List<ShipmentItemDto> fetchShipmentItems(Long shipmentId) {
        return query
                .select(new QShipmentItemDto(
                        shipmentItem.id,
                        shipmentItem.quantity,
                        shipmentItem.unitPrice,
                        shipmentItem.amount,
                        inventory.quantity.coalesce(BigDecimal.ZERO),
                        item.id,
                        item.code,
                        item.name,
                        item.type,
                        item.specification,
                        uom.id,
                        uom.code,
                        uom.scale,
                        lot.id,
                        lot.no
                ))
                .from(shipmentItem)
                .innerJoin(item).on(shipmentItem.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .leftJoin(lot).on(shipmentItem.lot.id.eq(lot.id))
                .leftJoin(inventory).on(
                        inventory.item.id.eq(item.id),
                        inventory.lot.id.eq(lot.id)
                                .or(inventory.lot.isNull().and(lot.isNull()))
                )
                .where(
                        notDeleted(shipmentItem.deletedAt),
                        eq(shipmentItem.shipment.id, shipmentId)
                )
                .orderBy(shipmentItem.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ShipmentItemDetailDto> fetchAllByLotId(Long lotId) {
        return query
                .select(shipmentItemDetailProjection)
                .from(shipmentItem)
                .innerJoin(shipment).on(shipmentItem.shipment.id.eq(shipment.id))
                .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                .innerJoin(item).on(shipmentItem.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .innerJoin(lot).on(shipmentItem.lot.id.eq(lot.id))
                .innerJoin(inventory).on(
                        inventory.item.id.eq(item.id),
                        inventory.lot.id.eq(lot.id)
                                .or(inventory.lot.isNull().and(lot.isNull()))
                )
                .where(
                        notDeleted(shipmentItem.deletedAt),
                        eq(shipmentItem.lot.id, lotId)
                )
                .orderBy(shipmentItem.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ShipmentItemDetailDto> fetchAllForExcel(ShipmentSearchDto dto) {
        return query
                .select(shipmentItemDetailProjection)
                .from(shipmentItem)
                .innerJoin(shipment).on(shipmentItem.shipment.id.eq(shipment.id))
                .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                .innerJoin(item).on(shipmentItem.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .innerJoin(lot).on(shipmentItem.lot.id.eq(lot.id))
                .innerJoin(inventory).on(
                        inventory.item.id.eq(item.id),
                        inventory.lot.id.eq(lot.id)
                                .or(inventory.lot.isNull().and(lot.isNull()))
                )
                .where(
                        notDeleted(shipment.deletedAt),
                        notDeleted(shipmentItem.deletedAt),
                        contains(shipment.shipmentNo, dto.getShipmentNo()),
                        containsAny(dto.getPartner(), partner.code, partner.name),
                        startDateGoe(shipment.date, dto.getStartDate()),
                        endDateLoe(shipment.date, dto.getEndDate())
                )
                .orderBy(shipmentItem.createdAt.desc())
                .fetch();
    }

    @Override
    public List<ShipmentItemDetailDto> fetchAllInLotId(List<Long> lotIds) {
        return query
                .select(shipmentItemDetailProjection)
                .from(shipmentItem)
                .innerJoin(shipment).on(shipmentItem.shipment.id.eq(shipment.id))
                .innerJoin(partner).on(shipment.partner.id.eq(partner.id))
                .innerJoin(item).on(shipmentItem.item.id.eq(item.id))
                .innerJoin(uom).on(item.uom.id.eq(uom.id))
                .innerJoin(lot).on(shipmentItem.lot.id.eq(lot.id))
                .innerJoin(inventory).on(
                        inventory.item.id.eq(item.id),
                        inventory.lot.id.eq(lot.id)
                                .or(inventory.lot.isNull().and(lot.isNull()))
                )
                .where(
                        notDeleted(shipmentItem.deletedAt),
                        in(shipmentItem.lot.id, lotIds)
                )
                .orderBy(shipmentItem.createdAt.desc())
                .fetch();
    }

    @Override
    public boolean existsShipmentItem(Long shipmentId, Long itemId, Long lotId) {
        return query
                .selectOne()
                .from(shipmentItem)
                .where(
                        notDeleted(shipmentItem.deletedAt),
                        eq(shipmentItem.shipment.id, shipmentId),
                        eq(shipmentItem.item.id, itemId),
                        eq(shipmentItem.lot.id, lotId)
                )
                .fetchFirst() != null;
    }
}

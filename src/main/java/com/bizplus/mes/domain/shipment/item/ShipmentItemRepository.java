package com.bizplus.mes.domain.shipment.item;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentItemRepository extends JpaRepository<ShipmentItem, Long>, ShipmentItemQueryRepository {

    Optional<ShipmentItem> findByIdAndDeletedAtIsNull(Long id);
}

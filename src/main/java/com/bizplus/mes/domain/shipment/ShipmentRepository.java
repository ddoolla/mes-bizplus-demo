package com.bizplus.mes.domain.shipment;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShipmentRepository extends JpaRepository<Shipment, Long>, ShipmentQueryRepository {

    Optional<Shipment> findByIdAndDeletedAtIsNull(Long id);
}

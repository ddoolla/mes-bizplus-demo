package com.bizplus.mes.domain.sensor;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {

    Optional<Sensor> findByCode(String code);

    boolean existsByCode(String code);
}

package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.sensor.event.dto.SensorEventDto;

import java.util.Optional;

public interface SensorEventQueryRepository {

    Optional<SensorEventDto> findCurrentData(Long sensorId, Long equipmentId);
}

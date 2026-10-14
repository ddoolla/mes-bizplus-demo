package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.sensor.event.dto.SensorEventCreateDto;
import com.bizplus.mes.domain.sensor.event.dto.SensorEventDto;

public interface SensorEventService {

    SensorEventDto getCurrentData(Long equipmentId, String sensorCode);

    void createSensorEvent(SensorEventCreateDto dto);
}

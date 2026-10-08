package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.sensor.event.dto.SensorEventCreateDto;

public interface SensorEventService {

    void createSensorEvent(SensorEventCreateDto dto);
}

package com.bizplus.mes.domain.sensor.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SensorCommand {

    EQUIPMENT_SENSOR_INIT("sensor.code.init"),
    EQUIPMENT_ALL_SENSOR_INIT("sensor.init"),
    ALL_EQUIPMENT_SENSORS_INIT("sensors.init");

    private final String value;
}

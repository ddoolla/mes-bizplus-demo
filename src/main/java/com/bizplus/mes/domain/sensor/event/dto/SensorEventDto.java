package com.bizplus.mes.domain.sensor.event.dto;

import com.bizplus.mes.domain.sensor.SensorDataType;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class SensorEventDto {

    private final Long id;
    private final String equipmentName;
    private final String sensorName;
    private final SensorDataType dataType;
    private final String stringValue;
    private final BigDecimal numericValue;
    private final Boolean running;
    private final LocalDateTime createdAt;

    @QueryProjection
    public SensorEventDto(Long id,
                          String equipmentName,
                          String sensorName,
                          SensorDataType dataType,
                          String stringValue,
                          BigDecimal numericValue,
                          Boolean running,
                          LocalDateTime createdAt) {
        this.id = id;
        this.equipmentName = equipmentName;
        this.sensorName = sensorName;
        this.dataType = dataType;
        this.stringValue = stringValue;
        this.numericValue = numericValue;
        this.running = running;
        this.createdAt = createdAt;
    }
}

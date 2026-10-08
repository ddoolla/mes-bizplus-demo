package com.bizplus.mes.domain.sensor.event.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SensorCommandPayload {

    @JsonProperty("sensorId") // 데이터 수집 프로그램 설정 파일의 senderId
    private final Long senderId;

    @JsonProperty("code") // 데이터 수집 프로그램 설정 파일의 propertyId
    private final String sensorCode;

    public SensorCommandPayload(Long senderId, String sensorCode) {
        this.senderId = senderId;
        this.sensorCode = sensorCode;
    }

    public SensorCommandPayload(Long senderId) {
        this(senderId, null);
    }

    public SensorCommandPayload() {
        this(null, null);
    }
}

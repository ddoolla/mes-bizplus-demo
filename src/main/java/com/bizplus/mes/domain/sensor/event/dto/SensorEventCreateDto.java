package com.bizplus.mes.domain.sensor.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Map;

@ToString
@Getter
@AllArgsConstructor
public class SensorEventCreateDto {

    /*
    * sensorId 속성키로 데이터를 보내주고 있는데,
    * 실제로는 니즈 프로그램 설정 파일의 senderId 값을 보내주고 있음.
    * */
    @NotNull
    @JsonProperty("sensorId")
    private Long senderId;

    @NotNull
    private LocalDateTime createdAt;

    @NotNull
    @NotEmpty
    private Map<String, Object> data;

    private MetaInfo meta;

    public record MetaInfo(
            String company,
            String location
    ) {
    }
}

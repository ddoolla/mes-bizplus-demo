package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.sensor.event.dto.SensorEventCreateDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sensor-events")
@RequiredArgsConstructor
public class SensorEventController {

    private final SensorEventService sensorEventService;

    // PLC 수집 데이터 저장 핸들러
    @PostMapping
    public ResponseEntity<Void> createSensorEvent(@RequestBody @Valid SensorEventCreateDto dto) {
        sensorEventService.createSensorEvent(dto);

        return ResponseEntity.ok().build();
    }
}

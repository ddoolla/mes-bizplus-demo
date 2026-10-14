package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.common.response.ApiResponse;
import com.bizplus.mes.domain.sensor.event.dto.SensorEventCreateDto;
import com.bizplus.mes.domain.sensor.event.dto.SensorEventDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensor-events")
@RequiredArgsConstructor
public class SensorEventController {

    private final SensorEventService sensorEventService;

    @GetMapping("/equipments/{equipmentId}/current")
    @ResponseBody
    public ResponseEntity<ApiResponse<SensorEventDto>> readCurrentData(@PathVariable Long equipmentId,
                                                                       @RequestParam String sensorCode) {
        return ResponseEntity.ok(
                ApiResponse.success(sensorEventService.getCurrentData(equipmentId, sensorCode)));
    }

    // PLC 수집 데이터 저장 핸들러
    @PostMapping
    public ResponseEntity<Void> createSensorEvent(@RequestBody @Valid SensorEventCreateDto dto) {
        sensorEventService.createSensorEvent(dto);

        return ResponseEntity.ok().build();
    }
}

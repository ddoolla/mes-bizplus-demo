package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.equipment.EquipmentReader;
import com.bizplus.mes.domain.sensor.Sensor;
import com.bizplus.mes.domain.sensor.SensorDataType;
import com.bizplus.mes.domain.sensor.SensorReader;
import com.bizplus.mes.domain.sensor.event.dto.SensorEventCreateDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorEventServiceImpl implements SensorEventService {

    private static final String RUNNING_SENSOR_CODE = "0001"; // 가동비가동 데이터 코드

    private final SensorEventRepository sensorEventRepository;

    private final EquipmentReader equipmentReader;
    private final SensorReader sensorReader;

    @Transactional
    @Override
    public void createSensorEvent(SensorEventCreateDto dto) {
        Equipment equipment = equipmentReader.getBySenderId(dto.getSenderId());

        Map<String, Sensor> sensorMap = sensorReader.getAll().stream()
                .collect(Collectors.toMap(Sensor::getCode, Function.identity()));

        Object runningRaw = dto.getData().get(RUNNING_SENSOR_CODE);
        Boolean running = runningRaw == null
                ? null
                : Boolean.parseBoolean(runningRaw.toString());

        List<SensorEvent> events = new ArrayList<>();

        for (Map.Entry<String, Object> entry : dto.getData().entrySet()) {
            String sensorCode = entry.getKey();
            Object value = entry.getValue();

            Sensor sensor = sensorMap.get(entry.getKey());

            if (sensor == null) {
                log.warn("정의되지 않은 센서 코드: {}", entry.getKey());
                continue;
            }

            if (entry.getValue() == null) {
                continue;
            }

            SensorDataType dataType = sensor.getDataType();
            String stringValue = null;
            BigDecimal numericValue = null;

            try {
                switch (dataType) {
                    case BOOLEAN -> numericValue = Boolean.parseBoolean(value.toString())
                            ? BigDecimal.ONE : BigDecimal.ZERO;
                    case INTEGER, DECIMAL -> numericValue = new BigDecimal(value.toString());
                    case STRING -> stringValue = value.toString();
                    default -> throw new IllegalArgumentException("지원하지 않는 데이터 타입: " + dataType);
                }
            } catch (IllegalArgumentException e) {
                log.warn("센서 값 변환 실패. code={}, value={}", sensorCode, value);
                continue;
            }

            events.add(new SensorEvent(
                    sensor,
                    equipment,
                    equipment.getName(),
                    sensor.getName(),
                    dataType,
                    stringValue,
                    numericValue,
                    running,
                    dto.getCreatedAt()
            ));
        }

        sensorEventRepository.saveAll(events);
    }
}

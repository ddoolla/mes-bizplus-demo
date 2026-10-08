package com.bizplus.mes.domain.sensor;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SensorInitializeService {

    private final SensorRepository sensorRepository;

    @Transactional
    public void initialize() {

        for (SensorCode sensorCode : SensorCode.values()) {
            if (sensorRepository.existsByCode(sensorCode.getCode())) {
                continue;
            }

            sensorRepository.save(new Sensor(
                    sensorCode.getCode(),
                    sensorCode.getName(),
                    sensorCode.getDataType(),
                    sensorCode.getUnit()
            ));
        }
    }
}

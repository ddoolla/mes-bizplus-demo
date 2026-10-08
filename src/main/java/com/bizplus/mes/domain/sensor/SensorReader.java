package com.bizplus.mes.domain.sensor;

import com.bizplus.mes.common.exception.BusinessException;
import com.bizplus.mes.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SensorReader {

    private final SensorRepository sensorRepository;

    public Sensor getByCode(String code) {
        return sensorRepository.findByCode(code)
                .orElseThrow(() -> new BusinessException(ErrorCode.SENSOR_NOT_FOUND, "code: " + code));
    }

    public List<Sensor> getAll() {
        return sensorRepository.findAll();
    }
}

package com.bizplus.mes.domain.sensor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SensorDataType {

    BOOLEAN,
    INTEGER,
    DECIMAL,
    STRING
}

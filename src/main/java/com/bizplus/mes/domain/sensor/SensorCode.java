package com.bizplus.mes.domain.sensor;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SensorCode {

    EQUIPMENT_RUNNING("0001", "설비 가동 상태", SensorDataType.BOOLEAN, null),
    OPERATION_STATUS("0002", "설비 동작 상태 코드", SensorDataType.STRING, null),
    CURRENT_PRODUCTION("0003", "현재 생산 수량", SensorDataType.INTEGER, null),
    TOTAL_PRODUCTION("0004", "누적 생산 수량", SensorDataType.INTEGER, null),
    CURRENT_DEFECT("0005", "현재 불량 수량", SensorDataType.INTEGER, null),
    TOTAL_DEFECT("0006", "누적 불량 수량", SensorDataType.INTEGER, null),
    TEMPERATURE("0007", "온도", SensorDataType.DECIMAL, "°C"),
    HUMIDITY("0008", "습도", SensorDataType.DECIMAL, "%RH"),
    INTERNAL_TEMPERATURE("0009", "설비 내부 온도", SensorDataType.DECIMAL, "°C"),
    EXTERNAL_TEMPERATURE("0010", "설비 외부 온도", SensorDataType.DECIMAL, "°C"),
    INTERNAL_HUMIDITY("0011", "설비 내부 습도", SensorDataType.DECIMAL, "%RH"),
    EXTERNAL_HUMIDITY("0012", "설비 외부 습도", SensorDataType.DECIMAL, "%RH"),
    CURRENT("0013", "전류", SensorDataType.DECIMAL, "A"),
    VOLTAGE("0014", "전압", SensorDataType.DECIMAL, "V"),
    POWER_CONSUMPTION("0015", "전력 소비량", SensorDataType.DECIMAL, "kW"),
    TOTAL_POWER_CONSUMPTION("0016", "누적 전력 사용량", SensorDataType.DECIMAL, "kWh"),
    VIBRATION_VELOCITY("0017", "진동 속도", SensorDataType.DECIMAL, "mm/s"),
    VIBRATION_FREQUENCY("0018", "진동 주파수", SensorDataType.DECIMAL, "Hz"),
    PNEUMATIC_PRESSURE("0019", "공압 압력", SensorDataType.DECIMAL, "bar"),
    HYDRAULIC_PRESSURE("0020", "유압 압력", SensorDataType.DECIMAL, "bar"),
    FLOW_RATE("0021", "유량", SensorDataType.DECIMAL, "L/min"),
    CO2_CONCENTRATION("0022", "CO₂ 농도", SensorDataType.DECIMAL, "ppm"),
    NOISE_LEVEL("0023", "소음 수준", SensorDataType.DECIMAL, "dB"),
    RPM("0024", "회전 속도", SensorDataType.DECIMAL, "RPM"),
    MOTOR_TEMPERATURE("0025", "모터 온도", SensorDataType.DECIMAL, "°C"),
    MOVING_SPEED("0026", "이동 속도", SensorDataType.DECIMAL, "mm/s"),
    ALARM("0027", "알람", SensorDataType.STRING, null),
    MOLD_TOP_TEMPERATURE("0028", "금형 상부 온도", SensorDataType.DECIMAL, "°C"),
    MOLD_BOTTOM_TEMPERATURE("0029", "금형 하부 온도", SensorDataType.DECIMAL, "°C");

    private final String code;
    private final String name;
    private final SensorDataType dataType;
    private final String unit;
}
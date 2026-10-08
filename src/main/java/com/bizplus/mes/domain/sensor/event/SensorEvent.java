package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.equipment.Equipment;
import com.bizplus.mes.domain.sensor.Sensor;
import com.bizplus.mes.domain.sensor.SensorDataType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "sensor_events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sensor_id", nullable = false)
    private Sensor sensor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipment_id", nullable = false)
    private Equipment equipment;

    private String equipmentName; // 설비 스냅샷
    private String sensorName; // 센서 스냅샷

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private SensorDataType dataType;

    private String stringValue;

    @Column(precision = 38, scale = 10)
    private BigDecimal numericValue;

    private Boolean running; // 가동 비가동 데이터는 따로 추가 했음.

    @JoinColumn(nullable = false)
    private LocalDateTime createdAt;

    public SensorEvent(Sensor sensor,
                       Equipment equipment,
                       String equipmentName,
                       String sensorName,
                       SensorDataType dataType,
                       String stringValue,
                       BigDecimal numericValue,
                       Boolean running,
                       LocalDateTime createdAt) {
        this.sensor = sensor;
        this.equipment = equipment;
        this.equipmentName = equipmentName;
        this.sensorName = sensorName;
        this.dataType = dataType;
        this.stringValue = stringValue;
        this.numericValue = numericValue;
        this.running = running;
        this.createdAt = createdAt;
    }
}

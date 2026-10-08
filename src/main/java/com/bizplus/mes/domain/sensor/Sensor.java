package com.bizplus.mes.domain.sensor;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sensors")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "varchar(255)", unique = true, nullable = false)
    private String code;

    private String name;

    @Column(columnDefinition = "varchar(255)", nullable = false)
    @Enumerated(EnumType.STRING)
    private SensorDataType dataType;

    private String unit;

    public Sensor(String code,
                  String name,
                  SensorDataType dataType,
                  String unit) {
        this.code = code;
        this.name = name;
        this.dataType = dataType;
        this.unit = unit;
    }
}

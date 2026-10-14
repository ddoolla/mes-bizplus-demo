package com.bizplus.mes.domain.sensor.event;

import com.bizplus.mes.domain.sensor.event.dto.QSensorEventDto;
import com.bizplus.mes.domain.sensor.event.dto.SensorEventDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.bizplus.mes.common.util.PredicateUtils.eq;
import static com.bizplus.mes.domain.sensor.event.QSensorEvent.sensorEvent;

@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SensorEventQueryRepositoryImpl implements SensorEventQueryRepository {

    private final JPAQueryFactory query;

    @Override
    public Optional<SensorEventDto> findCurrentData(Long sensorId, Long equipmentId) {
        return Optional.ofNullable(
                query
                        .select(new QSensorEventDto(
                                sensorEvent.id,
                                sensorEvent.equipmentName,
                                sensorEvent.sensorName,
                                sensorEvent.dataType,
                                sensorEvent.stringValue,
                                sensorEvent.numericValue,
                                sensorEvent.running,
                                sensorEvent.createdAt
                        ))
                        .from(sensorEvent)
                        .where(
                                eq(sensorEvent.equipment.id, equipmentId),
                                eq(sensorEvent.sensor.id, sensorId)
                        )
                        .orderBy(sensorEvent.createdAt.desc())
                        .fetchFirst()
        );
    }
}

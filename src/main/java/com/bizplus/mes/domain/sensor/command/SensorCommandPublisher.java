package com.bizplus.mes.domain.sensor.command;

import com.bizplus.mes.websocket.WebSocketPublisher;
import com.bizplus.mes.websocket.WsEnvelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SensorCommandPublisher {

    private static final String KPI_DESTINATION = "/kpi";

    private final WebSocketPublisher webSocketPublisher;

    /**
     * 장비의 특정 센서 초기화
     *
     * @param senderId   - Equipment.senderId (수집 프로그램 설정 파일의 senderId)
     * @param sensorCode - Sensor.code (수집 프로그램 설정 파일의 propertyId)
     */
    public void initEquipmentSensor(Long senderId, String sensorCode) {
        send(
                SensorCommand.EQUIPMENT_SENSOR_INIT,
                new SensorCommandPayload(senderId, sensorCode)
        );
    }

    /**
     * 장비의 전체 센서 초기화
     *
     * @param senderId - Equipment.senderId (수집 프로그램 설정 파일의 senderId)
     */
    public void initEquipmentAllSensors(Long senderId) {
        send(
                SensorCommand.EQUIPMENT_ALL_SENSOR_INIT,
                new SensorCommandPayload(senderId)
        );
    }

    /**
     * 전체 장비의 전체 센서 초기화
     */
    public void initAllEquipmentSensors() {
        send(
                SensorCommand.ALL_EQUIPMENT_SENSORS_INIT,
                new SensorCommandPayload()
        );
    }

    private void send(SensorCommand command, SensorCommandPayload payload) {
        var envelope = new WsEnvelope<>(command.getValue(), payload);
        webSocketPublisher.publish(KPI_DESTINATION, envelope);
        log.info("sensor command sent. command={}, messageId={}", command.getValue(), envelope.getMessageId());
    }
}

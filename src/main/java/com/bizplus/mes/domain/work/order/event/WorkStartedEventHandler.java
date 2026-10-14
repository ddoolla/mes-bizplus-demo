package com.bizplus.mes.domain.work.order.event;

import com.bizplus.mes.domain.sensor.command.SensorCommandPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class WorkStartedEventHandler {

    private final SensorCommandPublisher sensorCommandPublisher;

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(WorkStartedEvent event) {
        sensorCommandPublisher.initEquipmentAllSensors(event.senderId());
    }
}

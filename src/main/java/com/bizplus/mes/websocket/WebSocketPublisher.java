package com.bizplus.mes.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class WebSocketPublisher {

    private final SimpMessagingTemplate messagingTemplate;

    public void publish(String destination, WsEnvelope<?> envelope) {
        messagingTemplate.convertAndSend(destination, envelope);
    }
}

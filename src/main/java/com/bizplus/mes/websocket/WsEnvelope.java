package com.bizplus.mes.websocket;

import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
public class WsEnvelope<T> {

    private final String messageId;
    private final Instant timestamp;
    private final String command;
    private final T data;

    public WsEnvelope(String command, T data) {
        this.messageId = UUID.randomUUID().toString();
        this.timestamp = Instant.now();
        this.command = command;
        this.data = data;
    }
}

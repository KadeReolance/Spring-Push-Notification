package com.kade.NotificationService.Client.Components;

import com.kade.NotificationService.Server.Daos.OutgoingMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

@Slf4j
public class ClientSessionHandler extends StompSessionHandlerAdapter {
    @Override
    public Type getPayloadType(StompHeaders headers) {
        return OutgoingMessage.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, @Nullable Object payload) {
        if (payload != null) {
            log.info("Message Received: " + ((OutgoingMessage) payload).getContents());
        }else{
            log.error("Message Payload Undefined.");
        }
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        log.info("Connected");
    }

    @Override
    public void handleException(StompSession session, @Nullable StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        exception.printStackTrace();
        log.info("Exception" + exception.getLocalizedMessage());
    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {
        log.info("Transport Error");
    }
}

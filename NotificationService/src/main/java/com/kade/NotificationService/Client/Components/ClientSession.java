package com.kade.NotificationService.Client.Components;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kade.NotificationService.Server.Daos.IncomingMessage;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.concurrent.ExecutionException;

public class ClientSession {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        StompSession session = getStompSession();
        try {
            ObjectMapper objectMapper = new ObjectMapper(); // You can use any JSON serialization library
            while (true) {
                IncomingMessage message = new IncomingMessage("Test1 " + System.currentTimeMillis());

                session.send("/app/process-message", message);
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            session.disconnect();
        }
    }

    private static StompSession getStompSession() throws InterruptedException, ExecutionException {
        WebSocketClient client = new StandardWebSocketClient();

        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();

        ListenableFuture<StompSession> sessionListenableFuture =
                stompClient.connect("ws://localhost:8080/websocket-server", clientSessionHandler);

        StompSession session = sessionListenableFuture.get();
        session.subscribe("/topic/messages", clientSessionHandler);
        return session;
    }
}

package com.kade.NotificationService.Server.Controllers;

import com.kade.NotificationService.Server.Daos.IncomingMessage;
import com.kade.NotificationService.Server.Daos.OutgoingMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ServerController {

    @MessageMapping("/process-message") // /app/process-message
    @SendTo("/topic/messages")
    public OutgoingMessage processMessage(IncomingMessage incomingMessage) throws Exception {
        Thread.sleep(1000);
        return new OutgoingMessage("Hello " + incomingMessage.getName());
    }
}

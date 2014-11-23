package com.demo.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

/**
 * Created by yamorn on 2014/11/23.
 */
@Controller
public class WebSocketController {
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    public WebSocketController(SimpMessagingTemplate  messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping(value="/hello")
    public void send(@Payload String message,Principal user) {
        System.out.println(user.getName()+" message:"+message);
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            this.messagingTemplate.convertAndSend("/queue/"+user.getName(), "{\"say\":\"hi__" + i + "\"}");

        }
    }


}

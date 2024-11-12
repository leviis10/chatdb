package com.levi.chatdb.controllers;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.levi.chatdb.services.ChatService;
import com.levi.chatdb.utils.dtos.payload.ChatPayloadDTO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ChatService chatService;

    @MessageMapping("chat.sendMessage")
    public void sendMessage(@Payload ChatPayloadDTO request) {
        chatService.create(request);
        String destination = "/topic/" + request.getReceiverId();
        simpMessagingTemplate.convertAndSend(destination, request);
    }
}

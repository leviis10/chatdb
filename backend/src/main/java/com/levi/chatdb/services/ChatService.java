package com.levi.chatdb.services;

import org.springframework.stereotype.Service;

import com.levi.chatdb.models.Chat;
import com.levi.chatdb.models.ChatRoom;
import com.levi.chatdb.models.User;
import com.levi.chatdb.repositories.ChatRepository;
import com.levi.chatdb.utils.dtos.payload.ChatPayloadDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    public Chat create(ChatPayloadDTO request) {
        User sender = userService.getById(request.getSenderId());
        User receiver = userService.getById(request.getReceiverId());
        ChatRoom chatRoom = chatRoomService.getById(sender, request.getChatRoomId());

        return chatRepository.save(Chat.builder()
                .sender(sender)
                .receiver(receiver)
                .content(request.getContent())
                .chatRoom(chatRoom)
                .build());
    }
}

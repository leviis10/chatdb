package com.levi.chatdb.utils.dtos.responses;

import java.time.LocalDateTime;
import java.util.List;

import com.levi.chatdb.models.Chat;
import com.levi.chatdb.models.ChatRoom;
import com.levi.chatdb.models.User;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatRoomDTO {
    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private List<Long> ownerIds;

    private List<ChatDTO> chat;

    public static ChatRoomDTO toDTO(ChatRoom chatRoom) {
        return ChatRoomDTO.builder()
                .id(chatRoom.getId())
                .createdAt(chatRoom.getCreatedAt())
                .updatedAt(chatRoom.getUpdatedAt())
                .ownerIds(getOwnerIds(chatRoom.getOwner()))
                .chat(getChat(chatRoom.getChats()))
                .build();
    }

    private static List<Long> getOwnerIds(List<User> owners) {
        return owners.stream()
                .map(owner -> owner.getId())
                .toList();
    }

    private static List<ChatDTO> getChat(List<Chat> chats) {
        if (chats == null) {
            return null;
        }
        return chats.stream()
                .map(chat -> ChatDTO.toDTO(chat))
                .toList();
    }
}

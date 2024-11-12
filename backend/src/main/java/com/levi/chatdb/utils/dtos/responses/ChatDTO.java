package com.levi.chatdb.utils.dtos.responses;

import java.time.LocalDateTime;

import com.levi.chatdb.models.Chat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDTO {
    private Long senderId;

    private Long receiverId;

    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static ChatDTO toDTO(Chat chat) {
        return ChatDTO.builder()
                .senderId(chat.getSender().getId())
                .receiverId(chat.getReceiver().getId())
                .content(chat.getContent())
                .createdAt(chat.getCreatedAt())
                .updatedAt(chat.getUpdatedAt())
                .build();
    }
}

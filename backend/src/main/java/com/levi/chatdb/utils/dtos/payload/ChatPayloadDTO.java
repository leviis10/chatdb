package com.levi.chatdb.utils.dtos.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ChatPayloadDTO {
    @NotNull(message = "Sender ID cannot be null.")
    private Long senderId;

    @NotNull(message = "Receiver ID cannot be null.")
    private Long receiverId;

    @NotBlank(message = "Content cannot be blank.")
    private String content;

    @NotNull(message = "Chat Room ID cannot be blank.")
    private Long chatRoomId;
}

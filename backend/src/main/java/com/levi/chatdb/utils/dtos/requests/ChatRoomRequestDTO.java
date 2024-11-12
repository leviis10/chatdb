package com.levi.chatdb.utils.dtos.requests;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class ChatRoomRequestDTO {
    @NotNull(message = "Receiver ID cannot be null.")
    private Long receiverId;
}

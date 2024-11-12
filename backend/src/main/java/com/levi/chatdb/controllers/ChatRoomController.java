package com.levi.chatdb.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.levi.chatdb.exceptions.ChatRoomCreationException;
import com.levi.chatdb.models.ChatRoom;
import com.levi.chatdb.models.User;
import com.levi.chatdb.services.ChatRoomService;
import com.levi.chatdb.utils.contants.Endpoint;
import com.levi.chatdb.utils.dtos.requests.ChatRoomRequestDTO;
import com.levi.chatdb.utils.dtos.responses.ChatRoomDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(Endpoint.CHATS)
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @PostMapping
    public ResponseEntity<ChatRoomDTO> create(@AuthenticationPrincipal User user,
            @Valid @RequestBody ChatRoomRequestDTO request) throws ChatRoomCreationException {
        ChatRoom newChatRoom = chatRoomService.create(user, request);
        ChatRoomDTO response = ChatRoomDTO.toDTO(newChatRoom);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<ChatRoomDTO>> getAll(@AuthenticationPrincipal User user) {
        List<ChatRoom> chatRooms = chatRoomService.getAll(user);

        List<ChatRoomDTO> response = chatRooms.stream()
                .map(chatRoom -> ChatRoomDTO.toDTO(chatRoom))
                .toList();

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChatRoomDTO> getById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        ChatRoom chatRoom = chatRoomService.getById(user, id);
        ChatRoomDTO response = ChatRoomDTO.toDTO(chatRoom);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteById(@AuthenticationPrincipal User user, @PathVariable Long id) {
        chatRoomService.deleteById(user, id);
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Success", "Chat Room Deleted."));
    }
}

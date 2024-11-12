package com.levi.chatdb.services;

import java.util.List;

import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.levi.chatdb.exceptions.ChatRoomCreationException;
import com.levi.chatdb.models.ChatRoom;
import com.levi.chatdb.models.User;
import com.levi.chatdb.repositories.ChatRoomRepository;
import com.levi.chatdb.utils.dtos.requests.ChatRoomRequestDTO;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserService userService;

    @Transactional
    public ChatRoom create(User user, ChatRoomRequestDTO request) throws ChatRoomCreationException {
        if (user.getId() == request.getReceiverId()) {
            throw new ChatRoomCreationException("Receiver ID cannot be same with current user id",
                    HttpStatus.BAD_REQUEST);
        }

        List<User> owner = userService.getAllById(List.of(user.getId(), request.getReceiverId()));

        ChatRoom foundChatRoom = chatRoomRepository.findByExactOwners(owner, owner.size()).orElse(null);
        if (foundChatRoom != null) {
            Hibernate.initialize(foundChatRoom.getOwner());
            Hibernate.initialize(foundChatRoom.getChats());
            return foundChatRoom;
        }

        return chatRoomRepository.save(ChatRoom.builder()
                .owner(owner)
                .build());
    }

    @Transactional
    public List<ChatRoom> getAll(User user) {
        List<ChatRoom> chatRooms = chatRoomRepository.findByOwnerContaining(user);
        chatRooms.forEach(chatRoom -> {
            Hibernate.initialize(chatRoom.getChats());
            Hibernate.initialize(chatRoom.getOwner());
            chatRoom.getOwner().forEach(owner -> Hibernate.initialize(owner.getRoles()));
        });
        return chatRooms;
    }

    public ChatRoom getById(User user, Long id) {
        return chatRoomRepository.findByOwnerContainingAndId(user, id).orElseThrow();
    }

    public void deleteById(User user, Long id) {
        ChatRoom foundChatRoom = getById(user, id);
        chatRoomRepository.delete(foundChatRoom);
    }
}

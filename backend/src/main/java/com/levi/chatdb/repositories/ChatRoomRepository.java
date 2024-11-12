package com.levi.chatdb.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.levi.chatdb.models.ChatRoom;
import com.levi.chatdb.models.User;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
        List<ChatRoom> findByOwnerContaining(User user);

        Optional<ChatRoom> findByOwnerContainingAndId(User user, Long id);

        @Query("SELECT cr FROM ChatRoom cr WHERE SIZE(cr.owner) = :ownerSize " +
                        "AND (SELECT COUNT(u) FROM cr.owner u WHERE u IN :owners) = :ownerSize")
        Optional<ChatRoom> findByExactOwners(@Param("owners") List<User> owners,
                        @Param("ownerSize") int ownerSize);
}

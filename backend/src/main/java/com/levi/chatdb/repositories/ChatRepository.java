package com.levi.chatdb.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.levi.chatdb.models.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

}

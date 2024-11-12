package com.levi.chatdb.exceptions;

import org.springframework.http.HttpStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ChatRoomCreationException extends Exception {
    private HttpStatus statusCode;

    public ChatRoomCreationException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}

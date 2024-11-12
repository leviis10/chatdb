package com.levi.chatdb.controllers;

import java.util.List;
import java.util.Objects;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.levi.chatdb.exceptions.ChatRoomCreationException;
import com.levi.chatdb.utils.responses.ErrorResponse;

@RestControllerAdvice
public class GlobalError {
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
                        MethodArgumentNotValidException e) {
                List<String> errors = Objects.requireNonNull(e.getBindingResult()).getAllErrors().stream()
                                .map(exc -> exc.getDefaultMessage())
                                .toList();

                ErrorResponse response = ErrorResponse.builder()
                                .message("ERROR")
                                .status(HttpStatus.BAD_REQUEST.value())
                                .errors(errors)
                                .build();

                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        @ExceptionHandler(DataIntegrityViolationException.class)
        public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.builder()
                                .message("ERROR")
                                .status(HttpStatus.BAD_REQUEST.value())
                                .errors(List.of("Duplicate Key"))
                                .build());
        }

        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<ErrorResponse> handleAuthenticationException(AuthenticationException e) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.builder()
                                .message("ERROR")
                                .status(HttpStatus.UNAUTHORIZED.value())
                                .errors(List.of("Wrong username or password"))
                                .build());
        }

        @ExceptionHandler(ChatRoomCreationException.class)
        public ResponseEntity<ErrorResponse> handleChatRoomCreationException(ChatRoomCreationException e) {
                return ResponseEntity.status(e.getStatusCode()).body(ErrorResponse.builder()
                                .message("ERROR")
                                .status(e.getStatusCode().value())
                                .errors(List.of(e.getMessage()))
                                .build());
        }
}

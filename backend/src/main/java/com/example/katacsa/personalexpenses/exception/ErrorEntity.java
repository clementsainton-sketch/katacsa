package com.example.katacsa.personalexpenses.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorEntity(
        LocalDateTime timeStamp,
        String message,
        HttpStatus httpStatus
) {
}

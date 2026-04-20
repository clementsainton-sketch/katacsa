package com.example.katacsa.personalexpenses.exception;

import com.example.katacsa.personalexpenses.exception.exceptions.FutureDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.IncoherentDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.WrongAmountException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

import static com.example.katacsa.personalexpenses.exception.ErrorMessagesConstant.*;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(WrongAmountException.class)
    public ResponseEntity<ErrorEntity> wrongAmountHandler(WrongAmountException exception) {
        ErrorEntity error = new ErrorEntity(LocalDateTime.now(),
                AMOUNT_VALUE_ERROR,
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(error.httpStatus()).body(error);
    }

    @ExceptionHandler(IncoherentDateException.class)
    public ResponseEntity<ErrorEntity> incoherentDate(IncoherentDateException exception) {
        ErrorEntity error = new ErrorEntity(LocalDateTime.now(),
                INCOHERENT_DATES,
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(error.httpStatus()).body(error);
    }

    @ExceptionHandler(FutureDateException.class)
    public ResponseEntity<ErrorEntity> futureDate(IncoherentDateException exception) {
        ErrorEntity error = new ErrorEntity(LocalDateTime.now(),
                FUTURE_DATE_EXCEPTION,
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(error.httpStatus()).body(error);
    }
}

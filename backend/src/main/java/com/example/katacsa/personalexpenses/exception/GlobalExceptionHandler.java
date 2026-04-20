package com.example.katacsa.personalexpenses.exception;

import com.example.katacsa.personalexpenses.exception.exceptions.FutureDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.IncoherentDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.WrongAmountException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static com.example.katacsa.personalexpenses.exception.ErrorMessagesConstant.*;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        String answer = ex.getMessage().contains("must be greater than 0") ? "Amount must be greater than 0" : "Something went wrong";
        return ResponseEntity.status(status).body(answer);
    }

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
    public ResponseEntity<ErrorEntity> futureDate(FutureDateException exception) {
        ErrorEntity error = new ErrorEntity(LocalDateTime.now(),
                FUTURE_DATE_EXCEPTION,
                HttpStatus.BAD_REQUEST);

        return ResponseEntity.status(error.httpStatus()).body(error);
    }
}

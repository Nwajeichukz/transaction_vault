package com.mendusa.transactions.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionMapper {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleAsyncError(Exception ex) {
        // Handle the exception
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("An error occurred during async processing: " + ex.getMessage());
    }
}

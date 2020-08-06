package com.oklimenko.payment.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oklimenko.payment.dto.ErrorResult;
import com.oklimenko.payment.dto.Result;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpStatusCodeException;

@Slf4j
@AllArgsConstructor
@RestControllerAdvice
public class GlobalExceptionHandler {

    private ObjectMapper objectMapper;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResult> handleException(Exception ex) {
        log.error("An unknown error occurred.", ex);
        return new ResponseEntity<>(ErrorResult.error(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @SneakyThrows
    @ExceptionHandler(HttpStatusCodeException.class)
    public ResponseEntity<ErrorResult> handleException(HttpStatusCodeException ex) {
        log.error("An unknown error occurred.", ex);
        String errorPayload = ex.getResponseBodyAsString();
        ErrorResult errorResult = objectMapper.readValue(errorPayload, ErrorResult.class);

        return new ResponseEntity<ErrorResult>(errorResult, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
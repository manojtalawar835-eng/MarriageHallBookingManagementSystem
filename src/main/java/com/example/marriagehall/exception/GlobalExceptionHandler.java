package com.example.marriagehall.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private Map<String,Object> body(HttpStatus status, String message, Object details) {
        Map<String,Object> map = new LinkedHashMap<>();
        map.put("timestamp", LocalDateTime.now()); map.put("status", status.value());
        map.put("error", status.getReasonPhrase()); map.put("message", message);
        if (details != null) map.put("details", details);
        return map;
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> notFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body(HttpStatus.NOT_FOUND, ex.getMessage(), null));
    }
    @ExceptionHandler(BookingConflictException.class)
    public ResponseEntity<?> conflict(BookingConflictException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body(HttpStatus.CONFLICT, ex.getMessage(), null));
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> validation(MethodArgumentNotValidException ex) {
        Map<String,String> errors = new LinkedHashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(e -> errors.put(e.getField(), e.getDefaultMessage()));
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, "Please correct the highlighted fields.", errors));
    }
    @ExceptionHandler({IllegalArgumentException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<?> badRequest(Exception ex) {
        return ResponseEntity.badRequest().body(body(HttpStatus.BAD_REQUEST, "Invalid request: " + ex.getMessage(), null));
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> database(DataIntegrityViolationException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body(HttpStatus.CONFLICT, "The request conflicts with stored data.", null));
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> unexpected(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected server error occurred.", null));
    }
}

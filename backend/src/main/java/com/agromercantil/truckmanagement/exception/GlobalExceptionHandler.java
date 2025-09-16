package com.agromercantil.truckmanagement.exception;

import java.util.Map;
import javax.validation.ConstraintViolationException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Para validação de DTO (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Pega a primeira mensagem de erro
        String message = ex.getBindingResult().getFieldErrors()
            .stream()
            .findFirst()
            .map(err -> err.getDefaultMessage())
            .orElse("Dados inválidos");

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    // Para validação de entidade no persist (Hibernate)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException ex) {
        // Pega a primeira mensagem de erro
        String message = ex.getConstraintViolations()
            .stream()
            .findFirst()
            .map(cv -> cv.getMessage())
            .orElse("Dados inválidos");

        return ResponseEntity.badRequest().body(Map.of("message", message));
    }

    // Para BusinessException (como duplicidade de placa)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(409).body(Map.of("message", ex.getMessage()));
    }
}

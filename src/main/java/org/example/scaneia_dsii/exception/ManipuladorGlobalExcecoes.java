package org.example.scaneia_dsii.exception;

import jakarta.persistence.EntityNotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ManipuladorGlobalExcecoes {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> manipuladorRuntimeException(RuntimeException re){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erro de execução: " + re.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> manipuladorEntityNotFoundException(EntityNotFoundException enfe){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Plano não encontrado");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> manipuladorDatsIntegrityViolationException(DataIntegrityViolationException dive){
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Erro de DataIntegrityViolationException");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> manipuladorConstraintViolationException(ConstraintViolationException cve){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Erro de ContraintViolationException");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> manipuladorIllegalArgumentException(IllegalArgumentException iae){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("O corpo da sua validação não está validado!");
    }
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<Map<String, String>> manipuladorInvalidDataException(InvalidDataException ide){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ide.getErros());
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> manipuladorEmptyResultDataAccessException(EmptyResultDataAccessException erde){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("O plano não foi encontrado para ser deletado");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> manipuladorHttpMessageNotReadableException(HttpMessageNotReadableException hmnre){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("JSON mal formatado!");
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    manipulaMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(erro -> {
            erros.put(erro.getField(), erro.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(erros);
    }
}

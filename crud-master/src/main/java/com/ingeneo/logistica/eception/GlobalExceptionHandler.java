package com.ingeneo.logistica.eception;

import com.ingeneo.logistica.dto.Mensaje;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Mensaje> handleException(Exception ex) {
//        ex.printStackTrace();
//        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Mensaje(ex.getMessage()));
//    }

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<Mensaje> handleExceptionDb(GeneralException ex) {
        ex.printStackTrace();
        return ResponseEntity.status(ex.getStatus()).body(ex.getMensaje());
    }

}

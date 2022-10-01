package com.jitpay.locateuser.exception;


import com.jitpay.locateuser.util.Utility;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class AppExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        Map<String, String> map = new HashMap<>();
        Map<String, Object> responseMap = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                fieldError -> {
                    map.put(fieldError.getField(),fieldError.getDefaultMessage() );
                }
        );
        responseMap.put(Utility.ERROR_MESSAGE, map);
        return new ResponseEntity<>(responseMap, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        Map<String, String> map = new HashMap<>();
        if(ex.getCause().getMessage().contains("java.time.LocalDateTime")){
            map.put(Utility.ERROR_MESSAGE, Utility.INVALID_DATE);
        }else{
            map.put(Utility.ERROR_MESSAGE, ex.getCause().getMessage());
        }

        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFound(UserNotFoundException ex){
        Map<String, String> map = new HashMap<>();

        map.put(Utility.ERROR_MESSAGE, ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentException(IllegalArgumentException ex){
        Map<String, String> map = new HashMap<>();
        map.put(Utility.ERROR_MESSAGE, ex.getMessage());
        return new ResponseEntity<>(map, HttpStatus.BAD_REQUEST);

    }

    //global exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGlobalException(Exception exception){

        Map<String, String> map = new HashMap<>();
        map.put(Utility.ERROR_MESSAGE, exception.getMessage());

        return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}

package com.mingati.kikunditestrepo.controller.advice;

import com.mingati.kikunditestrepo.exception.CreationValidationException;
import com.mingati.kikunditestrepo.exception.TwilioError;
import com.mingati.kikunditestrepo.exception.UserLoginException;
import com.mingati.kikunditestrepo.response.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ResponseAdvice {

    @ExceptionHandler(CreationValidationException.class)
    public ResponseEntity<ApiResponse<?>> userNotFoundHandler(CreationValidationException ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .hasError(true).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserLoginException.class)
    public ResponseEntity<ApiResponse<?>> wrongCredentialHandler(UserLoginException ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .hasError(true).build(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(TwilioError.class)
    public ResponseEntity<ApiResponse<?>> trialErr(TwilioError ex) {
        return new ResponseEntity<>(ApiResponse.<String>builder()
                .errors(Collections.singletonList(ex.getMessage()))
                .hasError(true).build(), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        return new ResponseEntity<>(ApiResponse.builder().hasError(true).errors(errors).build(), HttpStatus.BAD_REQUEST);
    }


}
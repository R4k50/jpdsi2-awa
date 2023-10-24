package com.pizza.backend.handlers;

import com.pizza.backend.dtos.ErrorDto;
import com.pizza.backend.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(AppException ex)
    {
        List<String> errors = new ArrayList<>();
        errors.add(ex.getMessage());

        return ResponseEntity
            .status(ex.getStatus())
            .body(new ErrorDto(errors));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<ErrorDto> handleInvalidArgument(MethodArgumentNotValidException ex)
    {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(
            error -> {
                errors.add(error.getDefaultMessage());
            }
        );

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDto(errors));
    }
}
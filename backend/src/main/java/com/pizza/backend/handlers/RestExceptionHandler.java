package com.pizza.backend.handlers;

import com.pizza.backend.dtos.errors.ErrorDto;
import com.pizza.backend.exceptions.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler
{
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorDto> handleGeneralExceptions(Exception ex)
    {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<ErrorDto> handleRuntimeExceptions(RuntimeException ex)
    {
        System.out.println(ex);

        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorDto("Internal server error"));
    }

    @ExceptionHandler(AppException.class)
    public final ResponseEntity<ErrorDto> handleAppExceptions(AppException ex)
    {
        return ResponseEntity
            .status(ex.getStatus())
            .body(new ErrorDto(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDto> handleInvalidArgument(MethodArgumentNotValidException ex)
    {
        List<String> errors = ex.getBindingResult().getFieldErrors()
            .stream().map(ObjectError::getDefaultMessage).collect(Collectors.toList());

        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDto(errors));
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public final ResponseEntity<ErrorDto> handleMissingServletRequestPartExceptions(MissingServletRequestPartException ex)
    {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDto(ex.getRequestPartName() + " is required"));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public final ResponseEntity<ErrorDto> handleHttpMessageNotReadableExceptions(HttpMessageNotReadableException ex)
    {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(new ErrorDto("Provided value is not a number"));
    }
}
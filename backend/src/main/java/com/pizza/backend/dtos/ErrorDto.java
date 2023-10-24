package com.pizza.backend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Data
public class ErrorDto
{
    private List<String> errors = new ArrayList<>();

    public ErrorDto(String message)
    {
        errors.add(message);
    }
}
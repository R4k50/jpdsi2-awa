package com.pizza.backend.controllers;

import com.pizza.backend.dtos.*;
import com.pizza.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class ProductController
{
    private final ProductService productService;

    @GetMapping("/product/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Long id)
    {
        ProductDto productDto = productService.findById(id);

        return ResponseEntity.ok(productDto);
    }

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAll()
    {
        List<ProductDto> productDtos = productService.findAll();

        return ResponseEntity.ok(productDtos);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> add(@RequestBody @Valid NewProductDto newProductDto)
    {
        ProductDto productDto = productService.save(newProductDto);

        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDto> update(@RequestBody @Valid PatchProductDto patchProductDto,@PathVariable Long id)
    {
        ProductDto productDto = productService.update(patchProductDto, id);

        return ResponseEntity.ok(productDto);
    }

    @DeleteMapping("/product/{id}")
    public void delete(@PathVariable Long id)
    {
        productService.delete(id);
    }
}

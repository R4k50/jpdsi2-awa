package com.pizza.backend.controllers;

import com.pizza.backend.dtos.products.NewProductDto;
import com.pizza.backend.dtos.products.PatchProductDto;
import com.pizza.backend.dtos.products.ProductDto;
import com.pizza.backend.entities.Product;
import com.pizza.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Page<Product>> getAll(Pageable pageable, @RequestParam(required = false) String search)
    {
        if (search == null || search.split(",").length != 2)
        {
            Page<Product> products = productService.findAll(pageable);
            return ResponseEntity.ok(products);
        }

        Page<Product> products = productService.findAll(pageable, search);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/product")
    public ResponseEntity<ProductDto> add(@RequestBody @Valid NewProductDto newProductDto)
    {
        ProductDto productDto = productService.save(newProductDto);

        return ResponseEntity.ok(productDto);
    }

    @PatchMapping("/product/{id}")
    public ResponseEntity<ProductDto> update(@RequestBody @Valid PatchProductDto patchProductDto, @PathVariable Long id)
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

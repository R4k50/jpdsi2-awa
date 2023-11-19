package com.pizza.backend.services;

import com.pizza.backend.dtos.products.NewProductDto;
import com.pizza.backend.dtos.products.PatchProductDto;
import com.pizza.backend.dtos.products.ProductDto;
import com.pizza.backend.entities.Product;
import com.pizza.backend.exceptions.AppException;
import com.pizza.backend.mappers.ProductMapper;
import com.pizza.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductService
{
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  public ProductDto findById(Long id)
  {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));

    return productMapper.toProductDto(product);
  }

  public Page<Product> findAll(Pageable pageable)
  {
    Page<Product> products = productRepository.findAll(pageable);

    return products;
  }

  public ProductDto save(NewProductDto newProductDto)
  {
    Product product = productMapper.newProductDtoToProduct(newProductDto);
    Product savedProduct = productRepository.save(product);

    return productMapper.toProductDto(savedProduct);
  }

  public ProductDto update(PatchProductDto patchProductDto, Long id)
  {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));

    productMapper.update(product, patchProductDto);
    Product updatedProduct = productRepository.save(product);

    return productMapper.toProductDto(updatedProduct);
  }

  public void delete(Long id)
  {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new AppException("Unknown product", HttpStatus.NOT_FOUND));

    productRepository.delete(product);
  }
}

package com.pizza.backend.services;

import com.pizza.backend.dtos.NewProductDto;
import com.pizza.backend.dtos.PatchProductDto;
import com.pizza.backend.dtos.ProductDto;
import com.pizza.backend.entites.Product;
import com.pizza.backend.exceptions.AppException;
import com.pizza.backend.mappers.ProductMapper;
import com.pizza.backend.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

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

  public List<ProductDto> findAll()
  {
    List<Product> products = productRepository.findAll();

    return productMapper.toProductDtoList(products);
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

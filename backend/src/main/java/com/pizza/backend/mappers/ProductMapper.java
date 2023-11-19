package com.pizza.backend.mappers;

import com.pizza.backend.dtos.products.NewProductDto;
import com.pizza.backend.dtos.products.PatchProductDto;
import com.pizza.backend.dtos.products.ProductDto;
import com.pizza.backend.entities.Product;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper
{
  ProductDto toProductDto(Product product);
  Product newProductDtoToProduct(NewProductDto newProductDto);
  List<ProductDto> toProductDtoList(List<Product> product);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  void update(@MappingTarget Product product, PatchProductDto patchProductDto);
}

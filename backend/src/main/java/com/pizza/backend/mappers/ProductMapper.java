package com.pizza.backend.mappers;

import com.pizza.backend.dtos.NewProductDto;
import com.pizza.backend.dtos.PatchProductDto;
import com.pizza.backend.dtos.ProductDto;
import com.pizza.backend.entites.Product;
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

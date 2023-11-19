package com.pizza.backend.mappers;

import com.pizza.backend.dtos.orderedProducts.OrderedProductDto;
import com.pizza.backend.entities.Order;
import com.pizza.backend.entities.OrderedProduct;
import com.pizza.backend.entities.keys.OrderedProductKey;
import com.pizza.backend.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface OrderedProductMapper
{
  @Mapping(source = "orderedProduct.id.productId", target = "id")
  OrderedProductDto toOrderedProductDto(OrderedProduct orderedProduct);

  default List<OrderedProductDto> toOrderedProductDtoList(List<OrderedProduct> orderedProducts)
  {
    List<OrderedProductDto> orderedProductDtos = new ArrayList<>();

    for (OrderedProduct orderedProduct : orderedProducts)
      orderedProductDtos.add(toOrderedProductDto(orderedProduct));

    return orderedProductDtos;
  }

  default List<Long> orderedProductDtoListToIdList(List<OrderedProductDto> orderedProductDtos)
  {
    List<Long> productIds = new ArrayList<>();

    for (OrderedProductDto productDto : orderedProductDtos)
      productIds.add(productDto.getId());

    return productIds;
  }

  default List<OrderedProduct> toOrderedProductList(Order order, Map<Product, OrderedProductDto> orderedProductsMap)
  {
    List<OrderedProduct> orderedProducts = new ArrayList<>();

    orderedProductsMap.forEach(((product, orderedProductDto) -> {
      OrderedProductKey orderedProductKey = OrderedProductKey
        .builder()
          .orderId(order.getId())
          .productId(product.getId())
        .build();

      orderedProducts.add(
        OrderedProduct
          .builder()
            .id(orderedProductKey)
            .quantity(orderedProductDto.getQuantity())
          .build()
      );
    }));

    return orderedProducts;
  }
}
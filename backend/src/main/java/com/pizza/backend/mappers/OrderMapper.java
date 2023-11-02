package com.pizza.backend.mappers;

import com.pizza.backend.dtos.*;
import com.pizza.backend.entities.Order;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper
{
  @Mapping(source = "order.id", target = "id")
  @Mapping(source = "order.address", target = "address")
  @Mapping(source = "order.city", target = "city")
  @Mapping(source = "order.postalCode", target = "postalCode")
  @Mapping(source = "order.notes", target = "notes")
  @Mapping(source = "orderedProductDtos", target = "products")
  OrderDto toOrderDto(Order order, List<OrderedProductDto> orderedProductDtos);

  @Mapping(source = "order.id", target = "id")
  @Mapping(source = "order.address", target = "address")
  @Mapping(source = "order.city", target = "city")
  @Mapping(source = "order.postalCode", target = "postalCode")
  @Mapping(source = "order.notes", target = "notes")
  @Mapping(source = "order.user.id", target = "userId")
  @Mapping(source = "order.deliveryMan.id", target = "deliveryManId")
  @Mapping(source = "orderedProductDtos", target = "products")
  FullOrderDto toFullOrderDto(Order order, List<OrderedProductDto> orderedProductDtos);

  Order newOrderDtoToOrder(NewOrderDto newOrderDto);

  @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
  @Mapping(target = "user", ignore = true)
  @Mapping(target = "deliveryMan", ignore = true)
  void update(@MappingTarget Order order, PatchOrderDto patchOrderDto);
}

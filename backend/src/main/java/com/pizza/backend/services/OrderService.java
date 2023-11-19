package com.pizza.backend.services;

import com.pizza.backend.dtos.*;
import com.pizza.backend.entities.*;
import com.pizza.backend.exceptions.AppException;
import com.pizza.backend.mappers.OrderMapper;
import com.pizza.backend.mappers.OrderedProductMapper;
import com.pizza.backend.providers.UserAuthenticationProvider;
import com.pizza.backend.repositories.OrderRepository;
import com.pizza.backend.repositories.OrderedProductRepository;
import com.pizza.backend.repositories.ProductRepository;
import com.pizza.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RequiredArgsConstructor
@Service
public class OrderService
{
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderedProductRepository orderedProductRepository;
  private final OrderMapper orderMapper;
  private final OrderedProductMapper orderedProductMapper;
  private final UserAuthenticationProvider userAuthenticationProvider;

  public FullOrderDto findById(Long id)
  {
    Order order = orderRepository.findById(id)
      .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));

    List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());
    List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);

    return orderMapper.toFullOrderDto(order, orderedProductDtos);
  }

  public List<FullOrderDto> findAllByCurrentUser()
  {
    UserDto userDto = userAuthenticationProvider.getAuthenticatedUserDto();
    User user = userRepository.getById(userDto.getId());

    List<Order> orders = orderRepository.findAllByUser(user);
    List<FullOrderDto> fullOrderDtos = new ArrayList<>();

    for (Order order : orders)
    {
      List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());
      List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);

      FullOrderDto fullOrderDto = orderMapper.toFullOrderDto(order, orderedProductDtos);
      fullOrderDtos.add(fullOrderDto);
    }

    return fullOrderDtos;
  }

  public Page<FullOrderDto> findAll(Pageable pageable)
  {
    List<Order> orders = orderRepository.findAll();

    List<FullOrderDto> fullOrderDtos = new ArrayList<>();

    for (Order order : orders)
    {
      List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());
      List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);
      FullOrderDto fullOrderDto = orderMapper.toFullOrderDto(order, orderedProductDtos);

      fullOrderDtos.add(fullOrderDto);
    }

    return new PageImpl<>(fullOrderDtos, pageable, fullOrderDtos.size());
  }

  public Page<FullOrderDto> findAllAssigned(Pageable pageable)
  {
    UserDto userDto = userAuthenticationProvider.getAuthenticatedUserDto();
    User user = userRepository.getById(userDto.getId());

    List<Order> orders = orderRepository.findAllByDeliveryMan(user);

    List<FullOrderDto> fullOrderDtos = new ArrayList<>();

    for (Order order : orders)
    {
      List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());
      List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);
      FullOrderDto fullOrderDto = orderMapper.toFullOrderDto(order, orderedProductDtos);

      fullOrderDtos.add(fullOrderDto);
    }

    return new PageImpl<>(fullOrderDtos, pageable, fullOrderDtos.size());
  }

  public OrderDto save(NewOrderDto newOrderDto)
  {
    Order order = orderMapper.newOrderDtoToOrder(newOrderDto);

    UserDto userDto = userAuthenticationProvider.getAuthenticatedUserDto();
    User user = userRepository.getById(userDto.getId());
    order.setUser(user);

    List<OrderedProductDto> orderedProductDtos = newOrderDto.getProducts();
    List<Long> productIds = orderedProductMapper.orderedProductDtoListToIdList(orderedProductDtos);
    List<Product> products = productRepository.findAllById(productIds);

    if (products.isEmpty())
    {
      throw new AppException("No products found", HttpStatus.NOT_FOUND);
    }

    if (products.size() != orderedProductDtos.size())
    {
      throw new AppException("Some products do not exist", HttpStatus.NOT_FOUND);
    }

    Map<Product, OrderedProductDto> orderedProductsMap = IntStream
      .range(0, products.size())
      .boxed()
      .collect(
        Collectors.toMap(
          products::get,
          orderedProductDtos::get
        )
      );

    Order savedOrder = orderRepository.save(order);

    List<OrderedProduct> orderedProducts = orderedProductMapper.toOrderedProductList(savedOrder, orderedProductsMap);
    orderedProductRepository.saveAll(orderedProducts);

    return orderMapper.toOrderDto(savedOrder, orderedProductDtos);
  }

  public FullOrderDto update(PatchOrderDto patchOrderDto, Long id)
  {
    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));


    orderMapper.update(order, patchOrderDto);

    if (patchOrderDto.getUserId() != null)
    {
      User user = userRepository.findById(patchOrderDto.getUserId())
          .orElseThrow(() -> new AppException("Unknown user", HttpStatus.NOT_FOUND));

      order.setUser(user);
    }

    if (patchOrderDto.getDeliveryManId() != null)
    {
      User deliveryMan = userRepository.findById(patchOrderDto.getDeliveryManId())
          .orElseThrow(() -> new AppException("Unknown user (delivery man)", HttpStatus.NOT_FOUND));

      order.setDeliveryMan(deliveryMan);
    }

    Order updatedOrder = orderRepository.save(order);


    List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(updatedOrder.getId());
    List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);

    return orderMapper.toFullOrderDto(updatedOrder, orderedProductDtos);
  }

  public FullOrderDto assignToMe(Long id)
  {
    UserDto userDto = userAuthenticationProvider.getAuthenticatedUserDto();
    User user = userRepository.getById(userDto.getId());

    Order order = orderRepository.findById(id)
      .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));

    if (order.getDeliveryMan() != null)
    {
      throw new AppException("This order is assigned to someone else", HttpStatus.BAD_REQUEST);
    }

    order.setDeliveryMan(user);
    Order assignedOrder = orderRepository.save(order);

    List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(assignedOrder.getId());
    List<OrderedProductDto> orderedProductDtos = orderedProductMapper.toOrderedProductDtoList(orderedProducts);

    return orderMapper.toFullOrderDto(assignedOrder, orderedProductDtos);
  }

  public void deleteAssigned(Long id)
  {
    UserDto userDto = userAuthenticationProvider.getAuthenticatedUserDto();
    User user = userRepository.getById(userDto.getId());

    Order order = orderRepository.findById(id)
        .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));

    if (!user.equals(order.getDeliveryMan()))
    {
      throw new AppException("This order is not assigned to you", HttpStatus.BAD_REQUEST);
    }

    List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());

    for (OrderedProduct orderedProduct : orderedProducts)
      orderedProduct.setOrder(null);

    orderedProductRepository.deleteAll(orderedProducts);
    orderRepository.deleteById(order.getId());
  }

  public void delete(Long id)
  {
    Order order = orderRepository.findById(id)
      .orElseThrow(() -> new AppException("Unknown order", HttpStatus.NOT_FOUND));

    List<OrderedProduct> orderedProducts = orderedProductRepository.findAllByOrderId(order.getId());

    for (OrderedProduct orderedProduct : orderedProducts)
      orderedProduct.setOrder(null);

    orderedProductRepository.deleteAll(orderedProducts);
    orderRepository.deleteById(order.getId());
  }
}

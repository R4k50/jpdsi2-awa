package com.pizza.backend.controllers;

import com.pizza.backend.dtos.FullOrderDto;
import com.pizza.backend.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Validated
public class DeliveryController
{
  private final OrderService orderService;

  @GetMapping("/my-orders")
  public ResponseEntity<List<FullOrderDto>> getAll()
  {
    List<FullOrderDto> fullOrderDtos = orderService.findAllByCurrentUser();

    return ResponseEntity.ok(fullOrderDtos);
  }

  @GetMapping("/assigned-orders")
  public ResponseEntity<List<FullOrderDto>> getAllAssigned()
  {
    List<FullOrderDto> fullOrderDtos = orderService.findAllAssigned();

    return ResponseEntity.ok(fullOrderDtos);
  }

  @PatchMapping("/order/{id}/assign")
  public ResponseEntity<FullOrderDto> update(@PathVariable Long id)
  {
    FullOrderDto fullOrderDto = orderService.assignToMe(id);

    return ResponseEntity.ok(fullOrderDto);
  }

  @DeleteMapping("/assigned-order/{id}")
  public void delete(@PathVariable Long id)
  {
    orderService.deleteAssigned(id);
  }
}
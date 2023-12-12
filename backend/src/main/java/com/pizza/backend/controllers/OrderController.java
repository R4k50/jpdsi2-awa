package com.pizza.backend.controllers;

import com.pizza.backend.dtos.orders.FullOrderDto;
import com.pizza.backend.dtos.orders.NewOrderDto;
import com.pizza.backend.dtos.orders.OrderDto;
import com.pizza.backend.dtos.orders.PatchOrderDto;
import com.pizza.backend.services.OrderService;
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
public class OrderController
{
    private final OrderService orderService;

    @GetMapping("/order/{id}")
    public ResponseEntity<FullOrderDto> getOne(@PathVariable Long id)
    {
        FullOrderDto fullOrderDto = orderService.findById(id);

        return ResponseEntity.ok(fullOrderDto);
    }

    @GetMapping("/orders")
    public ResponseEntity<Page<FullOrderDto>> getAll(Pageable pageable, @RequestParam(required = false) String search)
    {
        if (search == null || search.split(",").length != 2)
        {
            Page<FullOrderDto> orders = orderService.findAll(pageable);
            return ResponseEntity.ok(orders);
        }

        Page<FullOrderDto> orders = orderService.findAll(pageable, search);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/order")
    public ResponseEntity<OrderDto> add(@RequestBody @Valid NewOrderDto newOrderDto)
    {
        OrderDto orderDto = orderService.save(newOrderDto);

        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/order/{id}")
    public ResponseEntity<FullOrderDto> update(@RequestBody @Valid PatchOrderDto patchOrderDto, @PathVariable Long id)
    {
        FullOrderDto fullOrderDto = orderService.update(patchOrderDto, id);

        return ResponseEntity.ok(fullOrderDto);
    }

    @DeleteMapping("/order/{id}")
    public void delete(@PathVariable Long id)
    {
        orderService.delete(id);
    }
}

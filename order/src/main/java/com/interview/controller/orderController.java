package com.interview.controller;


import com.interview.dto.OrderResponseDto;
import com.interview.entity.Order;
import com.interview.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class orderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public String placeOrder(@RequestBody Order  order)
    {
        return orderService.placeOrder(order);
    }


    @GetMapping("/{orderId}")
    public OrderResponseDto getOrder(@PathVariable String orderId)
    {
        return orderService.getOrder(orderId);

    }
}

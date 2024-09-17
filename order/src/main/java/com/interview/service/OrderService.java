package com.interview.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.dto.OrderResponseDto;
import com.interview.dto.PaymentDto;
import com.interview.dto.UserDto;
import com.interview.entity.Order;
import com.interview.repository.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@Service
public class OrderService {

    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${order.producer.topic.name}")
    private String topicname;


    public String placeOrder(Order order) {
        // save it in DB
        order.setPurchaseDate(new Date());
        order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
        orderRepo.save(order);

        // send it to payment using kafka
        try {
           // kafkaTemplate.send(topicname, order);
            kafkaTemplate.send(topicname, new ObjectMapper().writeValueAsString(order));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "Order with ordr has been placed ";

    }

    public OrderResponseDto getOrder( String  orderId) {

        //return null;

        Order order = orderRepo.findByOrderId(orderId);
        System.out.println("at order ");
        PaymentDto paymentDto = restTemplate.getForObject("http://PAYMENT-SERVICE/payments/"+orderId,PaymentDto.class);
        System.out.println("at paymentDto ");
        UserDto userDto = restTemplate.getForObject("http://USER-SERVICE/users/"+ order.getUserId(),UserDto.class);
        System.out.println("at userDto ");
        return OrderResponseDto.builder()
                .order(order)
                .paymentResponse(paymentDto)
                .userInfo(userDto)
                .build();



    }

}

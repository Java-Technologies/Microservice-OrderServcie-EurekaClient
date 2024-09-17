package com.interview.dto;

import com.interview.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponseDto {
    private String message;
    private Order order;
    private PaymentDto paymentResponse;
    private UserDto userInfo;

}

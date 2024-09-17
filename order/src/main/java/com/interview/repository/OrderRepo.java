package com.interview.repository;

import com.interview.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository <Order, Integer>{

public Order findByOrderId(String orderID);

}

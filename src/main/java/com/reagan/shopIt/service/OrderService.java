package com.reagan.shopIt.service;

import com.reagan.shopIt.model.dto.OrderDTO;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO);

    // Add more methods for order-related operations
}
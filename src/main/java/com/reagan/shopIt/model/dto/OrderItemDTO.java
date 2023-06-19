package com.reagan.shopIt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderItemId;
    private Long orderId;
    private Long productId;
    private int quantity;
    private double price;
}

package com.reagan.shopIt.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;

    @NotNull(message = "User ID is required")
    private Long userId;

    @NotNull(message = "Order date is required")
    private Date orderDate;

    @Valid
    @NotNull(message = "Order items are required")
    private List<OrderItemDTO> orderItems;

    // Other fields and validations
}

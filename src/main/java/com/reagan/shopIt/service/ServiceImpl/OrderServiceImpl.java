package com.reagan.shopIt.service.ServiceImpl;


import com.reagan.shopIt.model.domain.Order;
import com.reagan.shopIt.model.domain.OrderItem;
import com.reagan.shopIt.model.domain.Product;
import com.reagan.shopIt.model.domain.User;
import com.reagan.shopIt.model.dto.OrderDTO;
import com.reagan.shopIt.model.dto.ProductDTO;
import com.reagan.shopIt.repository.OrderRepository;
import com.reagan.shopIt.service.OrderService;
import com.reagan.shopIt.service.ProductService;
import com.reagan.shopIt.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, UserService userService,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        User user = userService.getUserById(orderDTO.getUserId());

        Order order = modelMapper.map(orderDTO, Order.class);
        order.setUser(user);

        List<OrderItem> orderItems = orderDTO.getOrderItems().stream()
                .map(orderItemDTO -> {
                    ProductDTO productById = productService.getProductById(orderItemDTO.getProductId());
                    Product product = modelMapper.map(productById, Product.class);
                    OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
                    orderItem.setProduct(product);
                    orderItem.setOrder(order);
                    return orderItem;
                })
                .collect(Collectors.toList());

        order.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(order);
        return modelMapper.map(savedOrder, OrderDTO.class);
    }

    // Implement other methods from the OrderService interface
}
//package com.reagan.shopIt.service.ServiceImpl;
//
//
//import com.reagan.shopIt.model.domain.PendingOrder;
//import com.reagan.shopIt.model.domain.Cart;
//import com.reagan.shopIt.model.domain.Item;
//import com.reagan.shopIt.model.domain.User;
//import com.reagan.shopIt.repository.OrderRepository;
//import com.reagan.shopIt.service.OrderService;
//import com.reagan.shopIt.service.ProductService;
//import com.reagan.shopIt.service.UserService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//
//@Service
//public class OrderServiceImpl implements OrderService {
//    private final OrderRepository orderRepository;
//    private final ProductService productService;
//    private final UserService userService;
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService, UserService userService,
//                            ModelMapper modelMapper) {
//        this.orderRepository = orderRepository;
//        this.productService = productService;
//        this.userService = userService;
//        this.modelMapper = modelMapper;
//    }
//
//    @Override
////    public OrderDTO createOrder(OrderDTO orderDTO) {
////        User user = userService.getUserById(orderDTO.getUserId());
////
////        PendingOrder pendingOrder = modelMapper.map(orderDTO, PendingOrder.class);
////        pendingOrder.setUser(user);
////
////        List<Cart> cartItems = orderDTO.getOrderItems().stream()
////                .map(orderItemDTO -> {
////                    ProductDTO productById = productService.getProductById(orderItemDTO.getProductId());
////                    Item products = modelMapper.map(productById, Item.class);
////                    Cart cartItem = modelMapper.map(orderItemDTO, Cart.class);
////                    cartItem.setItem(products);
////                    cartItem.setPendingOrder(pendingOrder);
////                    return cartItem;
////                })
////                .collect(Collectors.toList());
////
////        pendingOrder.setCartItems(cartItems);
////
////        PendingOrder savedPendingOrder = orderRepository.save(pendingOrder);
////        return modelMapper.map(savedPendingOrder, OrderDTO.class);
////    }
//
//    // Implement other methods from the OrderService interface
//}
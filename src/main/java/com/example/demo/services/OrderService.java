package com.example.demo.services;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.persistence.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;

    private final CartService cartService;

    public OrderService(OrderRepository orderRepository, CartService cartService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
    }

    public UserOrder save(UserOrder order) {
        return orderRepository.save(order);
    }

    public List<UserOrder> loadByUser(User user) {
        return orderRepository.findByUser(user);
    }

    public UserOrder createFromCart(Long cartId) {
        Cart cart = cartService.loadEagerById(cartId);
        UserOrder order = new UserOrder();
        order.setItems(new ArrayList<>(cart.getItems()));
        order.setTotal(cart.getTotal());
        order.setUser(cart.getUser());

        try {
            UserOrder savedOrder = save(order);
            log.info("Order for user " + cart.getUser().getUsername() + " was created successfully with id " + savedOrder.getId() + ".");
            return savedOrder;
        } catch (Exception ex) {
            log.info("The creation of the Order for user " + cart.getUser().getUsername() + " failed.");
            throw ex;
        }
    }
}

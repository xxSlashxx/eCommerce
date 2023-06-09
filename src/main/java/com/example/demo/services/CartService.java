package com.example.demo.services;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.CartRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.stream.IntStream;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart addToCart(Long id, Item item, int quantity) {
        Cart cart = loadEagerById(id);
        IntStream.range(0, quantity).forEach(i -> cart.addItem(item));
        return save(cart);
    }

    public Cart removeFromCart(Long id, Item item, int quantity) {
        Cart cart = loadEagerById(id);
        IntStream.range(0, quantity).forEach(i -> cart.removeItem(item));
        return save(cart);
    }

    public Cart loadEagerById(Long id) {
        Cart cart = cartRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cart with id " + id + " not found"));
        Hibernate.initialize(cart.getItems());
        return cart;
    }
}

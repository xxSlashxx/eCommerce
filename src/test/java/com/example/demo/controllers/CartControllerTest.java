package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CartControllerTest extends AbstractControllerTest {

    private CartController cartController;

    @Autowired
    public void setCartController(CartController cartController) {
        this.cartController = cartController;
    }

    @Test
    public void testAddToCart() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);

        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(createdUser.getUsername());

        ResponseEntity<Cart> addToCartResponse = cartController.addToCart(modifyCartRequest);
        assertNotNull(addToCartResponse);
        assertEquals(200, addToCartResponse.getStatusCodeValue());

        Cart cart = addToCartResponse.getBody();
        assertNotNull(cart);
        assertNotNull(cart.getItems());

        assertEquals(1, cart.getItems().size());
        assertEquals(Long.valueOf(1), cart.getItems().get(0).getId());
        assertEquals(new BigDecimal("2.99"), cart.getTotal());

        assertNotNull(cart.getUser());
        assertEquals(createdUser.getId(), cart.getUser().getId());
    }

    @Test
    public void testRemoveFromCart() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);

        ModifyCartRequest addCartRequest = new ModifyCartRequest();
        addCartRequest.setItemId(1L);
        addCartRequest.setQuantity(2);
        addCartRequest.setUsername(createdUser.getUsername());

        cartController.addToCart(addCartRequest);

        ModifyCartRequest removeFromCartRequest = new ModifyCartRequest();
        removeFromCartRequest.setItemId(1L);
        removeFromCartRequest.setQuantity(1);
        removeFromCartRequest.setUsername(createdUser.getUsername());

        ResponseEntity<Cart> removeFromCartResponse = cartController.removeFromCart(removeFromCartRequest);
        assertNotNull(removeFromCartResponse);
        assertEquals(200, removeFromCartResponse.getStatusCodeValue());

        Cart cart = removeFromCartResponse.getBody();
        assertNotNull(cart);
        assertNotNull(cart.getItems());

        assertEquals(1, cart.getItems().size());
        assertEquals(Long.valueOf(1), cart.getItems().get(0).getId());
        assertEquals(new BigDecimal("2.99"), cart.getTotal());

        assertNotNull(cart.getUser());
        assertEquals(createdUser.getId(), cart.getUser().getId());
    }
}
package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.UserOrder;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class OrderControllerTest extends AbstractControllerTest {

    private OrderController orderController;

    private CartController cartController;

    @Autowired
    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    @Autowired
    public void setCartController(CartController cartController) {
        this.cartController = cartController;
    }

    @Test
    public void testSubmit() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(createdUser.getUsername());
        cartController.addToCart(modifyCartRequest);
        ResponseEntity<UserOrder> submitResponse = orderController.submit(createdUser.getUsername());
        assertNotNull(submitResponse);
        assertEquals(200, submitResponse.getStatusCodeValue());
        UserOrder userOrder = submitResponse.getBody();
        assertNotNull(userOrder);
        assertNotNull(userOrder.getItems());
        assertEquals(1, userOrder.getItems().size());
        assertEquals(Long.valueOf(1), userOrder.getItems().get(0).getId());
        assertEquals(new BigDecimal("2.99"), userOrder.getTotal());
    }

    @Test
    public void testGetOrdersForUser() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setQuantity(1);
        modifyCartRequest.setUsername(createdUser.getUsername());
        cartController.addToCart(modifyCartRequest);
        orderController.submit(createdUser.getUsername());
        ResponseEntity<List<UserOrder>> ordersForUserResponse = orderController.getOrdersForUser(createdUser.getUsername());
        assertNotNull(ordersForUserResponse);
        assertEquals(200, ordersForUserResponse.getStatusCodeValue());
        List<UserOrder> userOrders = ordersForUserResponse.getBody();
        assertNotNull(userOrders);
        assertEquals(1, userOrders.size());
        assertEquals(createdUser.getUsername(), userOrders.get(0).getUser().getUsername());
        assertEquals(new BigDecimal("2.99"), userOrders.get(0).getTotal());
    }
}
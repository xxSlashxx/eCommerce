package com.example.demo.controllers;

import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.requests.ModifyCartRequest;
import com.example.demo.services.CartService;
import com.example.demo.services.ItemService;
import com.example.demo.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final UserService userService;

    private final CartService cartService;

    private final ItemService itemService;

    public CartController(UserService userService, CartService cartService, ItemService itemService) {
        this.userService = userService;
        this.cartService = cartService;
        this.itemService = itemService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<Cart> addToCart(@RequestBody ModifyCartRequest request) {
        User user = userService.loadByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemService.loadById(request.getItemId());

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart updatedCart = cartService.addToCart(user.getCart().getId(), item.get(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }

    @PostMapping("/removeFromCart")
    public ResponseEntity<Cart> removeFromCart(@RequestBody ModifyCartRequest request) {
        User user = userService.loadByUsername(request.getUsername());

        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Optional<Item> item = itemService.loadById(request.getItemId());

        if (!item.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Cart updatedCart = cartService.removeFromCart(user.getCart().getId(), item.get(), request.getQuantity());
        return ResponseEntity.ok(updatedCart);
    }
}
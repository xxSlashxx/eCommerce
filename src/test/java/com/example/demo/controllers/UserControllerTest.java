package com.example.demo.controllers;

import com.example.demo.model.persistence.User;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.*;

public class UserControllerTest extends AbstractControllerTest {

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Test
    public void testFindById() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        ResponseEntity<User> findByIdResponse = userController.findById(createdUser.getId());
        assertNotNull(findByIdResponse);
        assertEquals(200, findByIdResponse.getStatusCodeValue());
        User foundUser = findByIdResponse.getBody();
        assertNotNull(foundUser);
        assertEquals(createdUser.getId(), foundUser.getId());
    }

    @Test
    public void testFindByUsername() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        ResponseEntity<User> findByUserNameResponse = userController.findByUserName("user");
        assertNotNull(findByUserNameResponse);
        assertEquals(200, findByUserNameResponse.getStatusCodeValue());
        User foundUser = findByUserNameResponse.getBody();
        assertNotNull(foundUser);
        assertEquals(createdUser.getUsername(), foundUser.getUsername());
    }

    @Test
    public void testCreateUser() {
        ResponseEntity<User> createUserResponse = userController.createUser(createCreateUserRequest());
        assertNotNull(createUserResponse);
        assertEquals(200, createUserResponse.getStatusCodeValue());
        User createdUser = createUserResponse.getBody();
        assertNotNull(createdUser);
        assertEquals("user", createdUser.getUsername());
        assertTrue(passwordEncoder.matches("password", createdUser.getPassword()));
        assertNotNull(createdUser.getCart());
    }
}
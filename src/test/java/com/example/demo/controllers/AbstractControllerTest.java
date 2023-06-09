package com.example.demo.controllers;

import com.example.demo.model.requests.CreateUserRequest;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "classpath:/sql/delete-all-data.sql")
public abstract class AbstractControllerTest {

    protected UserController userController;


    @Autowired
    public void setUserController(UserController userController) {
        this.userController = userController;
    }




    protected CreateUserRequest createCreateUserRequest() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.setUsername("user");
        createUserRequest.setPassword("password");
        createUserRequest.setConfirmPassword("password");
        return createUserRequest;
    }
}
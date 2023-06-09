package com.example.demo.services;

//@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Invalid Password")
public class InvalidPasswordException extends RuntimeException {

    public InvalidPasswordException() {
    }

    public InvalidPasswordException(String message) {
        super(message);
    }
}
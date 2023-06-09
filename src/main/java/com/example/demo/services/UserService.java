package com.example.demo.services;

import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> loadById(Long id) {
        return userRepository.findById(id);
    }

    public User loadByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(User user) {
        try {
            User savedUser = userRepository.save(user);
            log.info("User with username " + savedUser.getUsername() + " was created successfully with id " + savedUser.getId() + ".");
            return savedUser;
        } catch (Exception ex) {
            log.info("The creation of the User with username " + user.getUsername() + " failed.");
            throw ex;
        }
    }

    public void checkPasswordPolicy(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new InvalidPasswordException("The creation of the user failed. Passwords are not equal.");
        }

        if (password.length() < 5) {
            throw new InvalidPasswordException("The creation of the user failed. The password is too short. The minimum length is 5.");
        }
    }
}
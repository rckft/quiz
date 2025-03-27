package dev.rckft.authservice.service;

import dev.rckft.authservice.controllers.request.UserRegisterRequest;
import dev.rckft.authservice.exception.UserAlreadyExistsException;
import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserRegistrationService.class);

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterRequest request) {
        String username = request.username();
        if (userExists(username)) {
            LOGGER.warn("Unable to register user {}. User with given username already exists", username);
            throw new UserAlreadyExistsException(username);
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        LOGGER.info("Registered user {}", username);
    }

    private boolean userExists(String username) {
        LOGGER.debug("Searching for existing user with username {}", username);
        boolean userExists = userRepository.findByUsername(username).isPresent();
        LOGGER.debug("Existing user with username {} {}", username, userExists ? "found": "not found");
        return userExists;
    }


}

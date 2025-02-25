package dev.rckft.authservice.service;

import dev.rckft.authservice.controllers.UserRegisterRequest;
import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserRegistrationService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserRegisterRequest request) throws Exception {
        if (userExists(request)) {
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
    }

    private boolean userExists(UserRegisterRequest request) {
        return userRepository.findByUsername(request.username()).isPresent();
    }


}

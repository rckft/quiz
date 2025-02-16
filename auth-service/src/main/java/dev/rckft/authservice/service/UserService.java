package dev.rckft.authservice.service;

import dev.rckft.authservice.controllers.UserRegisterRequest;
import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void register(UserRegisterRequest request) throws Exception {
        if (userExists(request)) {
            throw new Exception("User already exists");
        }

        User user = new User();
        user.setLogin(request.login());
        user.setPassword(request.password());
        userRepository.save(user);
    }

    private boolean userExists(UserRegisterRequest request) {
        return userRepository.findByLogin(request.login()) != null;
    }

}

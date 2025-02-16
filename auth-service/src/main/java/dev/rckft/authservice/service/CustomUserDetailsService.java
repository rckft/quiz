package dev.rckft.authservice.service;

import dev.rckft.authservice.model.user.User;
import dev.rckft.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username)); //albo jakiś RuntimeException

        return new org.springframework.security.core.userdetails.User( //możesz sobie zaimportować
                user.getUsername(),
                user.getPassword(),
                Collections.emptyList()
        );
    }


}

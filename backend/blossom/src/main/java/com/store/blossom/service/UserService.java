package com.store.blossom.service;

import com.store.blossom.dto.UserLoginDto;
import com.store.blossom.dto.UserRegistrationDto;
import com.store.blossom.model.Customer;
import com.store.blossom.model.User;
import com.store.blossom.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Registro de usuario
    public User register(UserRegistrationDto dto) {
        Customer user = new Customer();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword())); // 👈 encripta
        user.setEmail(dto.getEmail());
        user.setAddress(dto.getAddress());
        return userRepository.save(user);
    }

    // Validación de login
    public boolean login(UserLoginDto dto) {
        Optional<User> userOpt = userRepository.findByUsername(dto.getUsername());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return passwordEncoder.matches(dto.getPassword(), user.getPassword());
        }
        return false;
    }

    public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
}

}

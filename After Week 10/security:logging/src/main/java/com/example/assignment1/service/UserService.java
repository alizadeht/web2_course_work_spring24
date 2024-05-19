package com.example.assignment1.service;

import com.example.assignment1.model.dto.SignupDto;
import com.example.assignment1.model.entity.User;
import com.example.assignment1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerNewUser(SignupDto signUpDto) {
        User user = convertSignUpDtoToUser(signUpDto);
        userRepository.save(user);
    }

    private User convertSignUpDtoToUser(SignupDto signUpDto) {
        User user = new User();
        user.setUsername(signUpDto.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        user.setEmail(signUpDto.getEmail());
        user.setRoles("ROLE_USER"); // Assuming you have a method to set roles appropriately
        return user;
    }
}

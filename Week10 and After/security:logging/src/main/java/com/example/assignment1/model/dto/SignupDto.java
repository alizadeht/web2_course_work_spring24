package com.example.assignment1.model.dto;

import com.example.assignment1.model.entity.User;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.security.crypto.password.PasswordEncoder;
/**
 * Data Transfer Object (DTO) class for User entity.
 */
@Data
public class SignupDto {
    @NotBlank(message = "Username is required")
    @Size(min = 5, max = 20, message = "Username must be between 5 and 20 characters")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 8, max = 30, message = "Password must be between 8 and 30 characters")
    private String password;

    @NotBlank(message = "Email is required")
    @Size(min = 5, max = 50, message = "Email must be between 5 and 50 characters")
    private String email;

    public User toUser(PasswordEncoder encoder) {
        User user = new User();
        user.setUsername(this.username);
        user.setPassword(encoder.encode(this.password));
        user.setEmail(this.email);
        // Set the default roles or authorities as needed
        return user;
    }
}

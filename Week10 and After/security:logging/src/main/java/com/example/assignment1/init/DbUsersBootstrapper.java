package com.example.assignment1.init;

import com.example.assignment1.model.entity.User;
import com.example.assignment1.repository.UserRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DbUsersBootstrapper {

//    @Autowired
//    UserRepository userRepo;

    @Bean
    public ApplicationRunner init(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            if (userRepo.findByUsername("admin").isEmpty()) {
                User adminUser = new User("admin", encoder.encode("admin123"), "admin@example.com");
                adminUser.addRole("ROLE_ADMIN");
                userRepo.save(adminUser);
            }
            if (userRepo.findByUsername("user").isEmpty()) {
                User user = new User("user", encoder.encode("user123"), "user@example.com");
                user.addRole("ROLE_USER");
                userRepo.save(user);
            }
        };
    }

}


package com.example.lume.config;

import com.example.lume.model.User;
import com.example.lume.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByEmail("admin@lume.com").isEmpty()) {
                User user = new User();
                user.setNome("Admin");
                user.setEmail("admin@lume.com");
                user.setPassword("admin123");
                userRepository.save(user);
            }
        };
    }
}

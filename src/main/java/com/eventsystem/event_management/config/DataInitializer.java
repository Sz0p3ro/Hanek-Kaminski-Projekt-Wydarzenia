package com.eventsystem.event_management.config;

import com.eventsystem.event_management.model.Role;
import com.eventsystem.event_management.model.User;
import com.eventsystem.event_management.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {
    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@event.pl").isEmpty()) {
                User admin = new User("admin@event.pl", passwordEncoder.encode("admin123"), Role.ADMIN);
                userRepository.save(admin);
            }

            if (userRepository.findByEmail("user@event.pl").isEmpty()) {
                User user = new User("user@event.pl", passwordEncoder.encode("user123"), Role.USER);
                userRepository.save(user);
            }
        };
    }
}

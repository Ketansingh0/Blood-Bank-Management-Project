package com.bloodbank;

import com.bloodbank.model.User;
import com.bloodbank.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BloodBankApplication {
    public static void main(String[] args) {
        SpringApplication.run(BloodBankApplication.class, args);
    }

    // This will run after the app starts and create a test user if it doesn't exist
    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("admin@bloodbank.com").isEmpty()) {
                User admin = User.builder()
                        .email("admin@bloodbank.com")
                        .password(passwordEncoder.encode("admin123"))
                        .role("ROLE_ADMIN")
                        .build();
                userRepository.save(admin);
                System.out.println("✅ Test admin user created: admin@bloodbank.com / admin123");
            }
        };
    }
}

package com.enterprise.config;

import com.enterprise.entity.Role;
import com.enterprise.entity.User;
import com.enterprise.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Profile("dev")
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@enterprise.local");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRoles(Set.of(Role.ROLE_ADMIN));
            admin.setEnabled(true);
            userRepository.save(admin);
            System.out.println("Created default admin user: admin / Admin@123 (dev profile)");
        }
    }
}

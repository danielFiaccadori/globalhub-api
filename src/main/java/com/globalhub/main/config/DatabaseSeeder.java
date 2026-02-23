package com.globalhub.main.config;

import com.globalhub.main.domain.user.User;
import com.globalhub.main.domain.user.UserRole;
import com.globalhub.main.repository.UserRepository;
import com.globalhub.main.utils.RggGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RggGenerator rggGenerator;

    @Value("${globalhub.admin.email}")
    private String adminEmail;

    @Value("${globalhub.admin.password}")
    private String adminPassword;

    public DatabaseSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder, RggGenerator rggGenerator) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rggGenerator = rggGenerator;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmailOrRgg(adminEmail).isEmpty()) {
            System.out.println("⚠️ Super Admin not found. Creating root user...");

            String encryptedPassword = passwordEncoder.encode(adminPassword);
            String rgg = rggGenerator.generateNewRgg();

            User superAdmin = new User(adminEmail, encryptedPassword, rgg, UserRole.ADMIN);

            userRepository.save(superAdmin);
            System.out.println("✅ Super Admin created successfully! E-mail: " + adminEmail);
        } else {
            System.out.println("✅ Super Admin already created in database. Secure initialization.");
        }
    }
}

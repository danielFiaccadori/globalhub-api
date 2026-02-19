package com.globalhub.main.utils;

import com.globalhub.main.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class RggGenerator {

    private final UserRepository repository;
    private final SecureRandom random = new SecureRandom();

    public RggGenerator(UserRepository repository) {
        this.repository = repository;
    }

    public String generateNewRgg() {
        String generatedRgg;

        do {
            int number = random.nextInt(100000, 1000000);
            generatedRgg = Integer.toString(number);
        } while (repository.existsByRgg(generatedRgg));

        return generatedRgg;
    }

}

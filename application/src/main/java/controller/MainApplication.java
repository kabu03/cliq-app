package controller;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = {"validators", "controller", "repositories", "services", "usecases"})
@EnableJpaRepositories(basePackages = "repositories")
@EntityScan(basePackages = "models")
public class MainApplication {

    public static void main(String[] args) {
//// Create a PasswordEncoder instance
//        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//
//        // Encode the password
//        String rawPassword = "password123";
//        String encodedPassword = passwordEncoder.encode(rawPassword);
//        // Print the encoded password
//        System.out.println("Encoded password: " + encodedPassword);
        SpringApplication.run(MainApplication.class, args);
    }
}

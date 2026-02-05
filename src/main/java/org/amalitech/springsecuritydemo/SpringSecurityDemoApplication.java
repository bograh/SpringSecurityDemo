package org.amalitech.springsecuritydemo;

import org.amalitech.springsecuritydemo.entity.Post;
import org.amalitech.springsecuritydemo.repository.PostRepository;
import org.amalitech.springsecuritydemo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringSecurityDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PostRepository postRepository, UserRepository userRepository, PasswordEncoder encoder) {

        return args -> {
//            userRepository.save(new User("Regular User", "user", encoder.encode("password"), "ROLE_USER"));
//            userRepository.save(new User("Admin User", "admin", encoder.encode("password"), "ROLE_USER,ROLE_ADMIN"));
            postRepository.save(new Post("Hello World", "hello-world", "Welcome to blog", "ben"));
        };
    }

}
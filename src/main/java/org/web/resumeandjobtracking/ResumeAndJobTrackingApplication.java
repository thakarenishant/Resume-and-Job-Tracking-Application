package org.web.resumeandjobtracking;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.web.resumeandjobtracking.Model.User;
import org.web.resumeandjobtracking.repo.UserRepository;

@SpringBootApplication
public class ResumeAndJobTrackingApplication {

    public static void main(String[] args) {
        SpringApplication.run(ResumeAndJobTrackingApplication.class, args);
    }
}

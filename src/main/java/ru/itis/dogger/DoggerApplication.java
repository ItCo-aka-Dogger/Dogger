package ru.itis.dogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.itis.dogger.services.EmailService;
import ru.itis.dogger.services.EmailServiceImpl;

import java.util.UUID;

@SpringBootApplication
public class DoggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DoggerApplication.class, args);
    }
}

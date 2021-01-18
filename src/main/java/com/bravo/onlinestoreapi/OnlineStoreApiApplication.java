package com.bravo.onlinestoreapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OnlineStoreApiApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(OnlineStoreApiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }
}

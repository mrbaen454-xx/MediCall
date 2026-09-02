package com.hospital;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


// SecurityAutoConfiguration is now enabled
@SpringBootApplication
public class MediCallApplication {

    public static void main(String[] args) {
        SpringApplication.run(MediCallApplication.class, args);
    }
}

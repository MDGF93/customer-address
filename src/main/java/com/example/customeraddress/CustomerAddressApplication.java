package com.example.customeraddress;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class CustomerAddressApplication {

    public static void main(String[] args) {
        //print spring boot version
        System.out.println("Spring Boot Version: " + SpringBootVersion.getVersion());
        //print spring version
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        SpringApplication.run(CustomerAddressApplication.class, args);
    }

}

package com.example.customeraddress;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class CustomerAddressApplication implements ApplicationRunner {


    //Load fakedata.csv and map each column to a CustomerAddressDTO object
    //Save each CustomerAddressDTO object to the database
    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
    public static void main(String[] args) {
        //print spring boot version
        System.out.println("Spring Boot Version: " + SpringBootVersion.getVersion());
        //print spring version
        System.out.println("Spring Version: " + SpringVersion.getVersion());
        SpringApplication.run(CustomerAddressApplication.class, args);
    }


}

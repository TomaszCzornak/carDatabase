package com.tomek.cardatabase;

import com.tomek.cardatabase.domain.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@RequiredArgsConstructor
@SpringBootApplication
public class CardatabaseApplication implements CommandLineRunner {

    private static final Logger logger =
            LoggerFactory.getLogger(CardatabaseApplication.class);
    private final OwnerRepository ownerRepository;
    private final UserRepository userRepository;
    private final CarRepository carRepository;

    public static void main(String[] args) {
        SpringApplication.run(CardatabaseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Owner owner1 = new Owner("John", "Johnson");
        Owner owner2 = new Owner("Mary", "Robinson");
        Owner owner3 = new Owner("Tomek", "Czornak");
        ownerRepository.saveAll(Arrays.asList(owner1,
                owner2, owner3));
        // Add car object and link to owners and save these to db
        Car car1 = new Car("Ford", "Mustang", "Red",
                "ADF-1121", 2021, 59000, owner1);
        Car car2 = new Car("Nissan", "Leaf", "White",
                "SSJ-3002", 2019, 29000, owner2);
        Car car3 = new Car("Toyota", "Prius", "Silver",
                "KKO-0212", 2020, 39000, owner2);
        Car car4 = new Car("Honda", "Civic", "Black",
                "KR-8Y616", 2007, 25000, owner3);
        carRepository.saveAll(Arrays.asList(car1, car2, car3, car4));

        for (Car car : carRepository.findAll()) {
            logger.info(car.getBrand() + " " + car.getModel());
        }
        // Username: user, password: user
        userRepository.save(new UserEntity("user",
                "$2a$10$NVM0n8ElaRgg7zWO1CxUdei7vWoPg91Lz2aYavh9.f9q0e4bRadue", "USER"));

        // Username: admin, password: admin
        userRepository.save(new UserEntity("admin", "$2a$10$8cjz47bjbR4Mn8GMg9IZx.vyjhLXR/SKKMSZ9.mP9vpMu0ssKi8GW", "ADMIN"));
    }
}

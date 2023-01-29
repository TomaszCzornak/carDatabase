package com.tomek.cardatabase.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    private String brand, model, color, registerNumber;
    private int years, price;

    public Car() {}

    public Car(String brand, String model, String color, String registerNumber,
               int years, int price, Owner owner) {
        super();
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.registerNumber = registerNumber;
        this.years = years;
        this.price = price;
        this.owner = owner;
    }


}

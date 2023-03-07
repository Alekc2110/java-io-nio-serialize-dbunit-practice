package com.epam.alexkorshunovych.serialization.model;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Customer extends Person {
    private final String name;
    private final String phone;
    private final transient int age;
    private final boolean active;
    private final LocalDate joined;

    public Customer(long id, String name, String phone, int age, boolean active, LocalDate joined) {
        super(id);
        this.name = name;
        this.phone = phone;
        this.age = age;
        this.active = active;
        this.joined = joined;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + super.getId() + ", " +
                "name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", age=" + age +
                ", active=" + active +
                ", joined=" + joined +
                '}';
    }
}




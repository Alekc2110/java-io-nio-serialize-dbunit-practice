package com.epam.alexkorshunovych.serialization.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
public class Customers implements Serializable {
    private final List<Customer> customers;
}

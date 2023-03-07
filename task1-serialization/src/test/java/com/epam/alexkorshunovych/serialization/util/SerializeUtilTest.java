package com.epam.alexkorshunovych.serialization.util;

import com.epam.alexkorshunovych.serialization.model.Customer;
import com.epam.alexkorshunovych.serialization.model.Customers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SerializeUtilTest {

    private static final String PATH = "customers";

    @Test
    @Order(1)
    public void serializeToFileTest() throws IOException {
        //given
        Files.deleteIfExists(Path.of(PATH));
        Customers customers = createCustomers();
        //when
        SerializeUtil.serializeToFile(customers, PATH);
        //then
        assertTrue(Files.exists(Paths.get(PATH)));


    }

    @Test
    @Order(2)
    public void deserializeFromFile(){
        //when
        Customers customers = SerializeUtil.deserializeFromFile(PATH);
        //then
        System.out.println(customers);
        customers.getCustomers().forEach(
                customer -> assertAll(()-> {
                    assertEquals(0, customer.getId());
                    assertEquals(0, customer.getAge());
                })
        );
    }

    private static Customers createCustomers() {
        Customer customer1 =
                new Customer(1, "John", "+380507788999", 55, true, LocalDate.of(2021, 12, 5));
        Customer customer2 =
                new Customer(1, "Samanta", "+380985598877", 30, true, LocalDate.of(2023, 1, 25));
        ArrayList<Customer> customersList = new ArrayList<>(Arrays.asList(customer1, customer2));
        return new Customers(customersList);
    }
}
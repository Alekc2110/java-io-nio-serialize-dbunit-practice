package com.epam.alexkorshunovych.serialization.util;

import com.epam.alexkorshunovych.serialization.model.Customers;

import java.io.*;

public class SerializeUtil {

    public static void serializeToFile(Object customers, String path) {
        try (ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(path)))) {
            os.writeObject(customers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Customers deserializeFromFile(String path) {
        Customers customers = null;
        try (ObjectInputStream os = new ObjectInputStream(new BufferedInputStream(new FileInputStream(path)))) {
           customers = (Customers) os.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return customers;
    }

}

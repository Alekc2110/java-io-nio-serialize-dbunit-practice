package com.epam.alexkorshunovych.serialization.model;

import lombok.*;

import java.io.Serializable;


@Getter
@AllArgsConstructor
public abstract class Person implements Serializable {
    private final transient long id;

}

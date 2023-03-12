package com.epam.alexkorshunovych.dbunit.repository.interfaces;

import com.epam.alexkorshunovych.dbunit.entity.Patient;

import java.util.List;

public interface PatientRepository {

    List<Patient> findAll();

    Long save(Patient patient);

    int deletePatientsGreaterThen(int age);
}

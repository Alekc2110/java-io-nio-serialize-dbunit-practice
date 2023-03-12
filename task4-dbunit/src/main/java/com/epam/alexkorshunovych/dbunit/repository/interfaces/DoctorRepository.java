package com.epam.alexkorshunovych.dbunit.repository.interfaces;

import com.epam.alexkorshunovych.dbunit.entity.Doctor;

import java.util.List;

public interface DoctorRepository {

    List<Doctor> findAll();

    Long save(Doctor doctor);

    void delete(Long id);

    Doctor findById(Long id);
}

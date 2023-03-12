package com.epam.alexkorshunovych.dbunit.repository;

import com.epam.alexkorshunovych.dbunit.config.HibernateSessionFactory;
import com.epam.alexkorshunovych.dbunit.entity.Patient;
import com.epam.alexkorshunovych.dbunit.repository.interfaces.PatientRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class JpaPatientRepository implements PatientRepository {

    @Override
    public List<Patient> findAll() {
        List<Patient> list = new ArrayList<>();
        try (Session session = HibernateSessionFactory.getSession()) {
            list = session.createQuery("SELECT p FROM Patient p", Patient.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Long save(Patient patient) {
        Transaction tr = null;
        Long id = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            tr = session.beginTransaction();
            session.persist(patient);
            id = patient.getId();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public int deletePatientsGreaterThen(int age) {
        Transaction tr = null;
        int numberDelEntities = 0;
        try (Session session = HibernateSessionFactory.getSession()) {
            tr = session.beginTransaction();
            numberDelEntities = session.createQuery("""
                            delete from Patient where age > :givenAge
                            """)
                    .setParameter("givenAge", age)
                    .executeUpdate();
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }

        return numberDelEntities;

    }

}

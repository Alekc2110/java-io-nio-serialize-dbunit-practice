package com.epam.alexkorshunovych.dbunit.repository;

import com.epam.alexkorshunovych.dbunit.config.HibernateSessionFactory;
import com.epam.alexkorshunovych.dbunit.entity.Doctor;
import com.epam.alexkorshunovych.dbunit.repository.interfaces.DoctorRepository;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class JpaDoctorRepository implements DoctorRepository {

    @Override
    public List<Doctor> findAll() {
        List<Doctor> list = new ArrayList<>();
        try (Session session = HibernateSessionFactory.getSession()) {
            list = session.createQuery("SELECT d FROM Doctor d", Doctor.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Long save(Doctor doctor) {
        Transaction tr = null;
        Long id = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            tr = session.beginTransaction();
            session.persist(doctor);
            id = doctor.getId();
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
    public void delete(Long id) {
        Transaction tr = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            tr = session.beginTransaction();
            Doctor doctor = session.find(Doctor.class, id);
            session.remove(doctor);
            tr.commit();
        } catch (Exception e) {
            if (tr != null) {
                tr.rollback();
            }
            e.printStackTrace();
        }
    }

    @Override
    public Doctor findById(Long id) {
        Doctor doctor = null;
        try (Session session = HibernateSessionFactory.getSession()) {
            doctor = session.find(Doctor.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return doctor;
    }
}

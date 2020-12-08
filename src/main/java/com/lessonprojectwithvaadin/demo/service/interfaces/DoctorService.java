package com.lessonprojectwithvaadin.demo.service.interfaces;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;

import java.util.List;

public interface DoctorService {

    List<Doctor> findAll();

    Doctor getOneById(Long id);

    void save(Doctor doctor);

    void deleteById(Long id);

    List<Doctor> findByName(String filter);

    void delete(Doctor doctor);

    int amountDoctors();

    int amountPatients();

    int amountRecipes();
}

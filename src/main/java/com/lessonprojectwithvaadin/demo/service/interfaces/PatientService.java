package com.lessonprojectwithvaadin.demo.service.interfaces;

import com.lessonprojectwithvaadin.demo.dao.entity.Patient;

import java.util.List;

public interface PatientService {

    List<Patient> findAll();

    List<Patient> filter(String filter);

    Patient getById(Long id);

    void save(Patient patient);

    void deleteById(Long id);

    void delete(Patient patient);
}

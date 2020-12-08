package com.lessonprojectwithvaadin.demo.service.implemention;

import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.dao.repository.PatientRepository;
import com.lessonprojectwithvaadin.demo.service.interfaces.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    @Autowired
    private final PatientRepository patientRepo;

    @Override
    public List<Patient> findAll() {
        return patientRepo.findAll();
    }

    @Override
    public List<Patient> filter(String filter) {
        return patientRepo.findByName(filter);
    }

    @Override
    public Patient getById(Long id) {
        Optional<Patient> patient = Optional.of(patientRepo.getOne(id));
        return patient.get();
    }

    @Override
    public void save(Patient patient) {
        patientRepo.save(patient);
    }

    @Override
    public void deleteById(Long id) {
        patientRepo.deleteById(id);
    }

    @Override
    public void delete(Patient patient) {
        patientRepo.delete(patient);
    }
}

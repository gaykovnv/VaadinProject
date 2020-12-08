package com.lessonprojectwithvaadin.demo.service.implemention;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.dao.repository.DoctorRepository;
import com.lessonprojectwithvaadin.demo.dao.repository.PatientRepository;
import com.lessonprojectwithvaadin.demo.dao.repository.RecipeRepository;
import com.lessonprojectwithvaadin.demo.service.interfaces.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private final DoctorRepository doctorRepo;

    @Autowired
    private final PatientRepository patientRepo;

    @Autowired
    private final RecipeRepository recipeRepo;

    @Override
    public List<Doctor> findAll() {
        return doctorRepo.findAll();
    }

    @Override
    public Doctor getOneById(Long id) {
        Optional<Doctor> doctor = doctorRepo.findById(id);
        return doctor.get();
    }

    @Override
    public void save(Doctor doctor) {
        doctorRepo.save(doctor);
    }

    @Override
    public void deleteById(Long id) {
        doctorRepo.deleteById(id);
    }

    @Override
    public List<Doctor> findByName(String filter) {
        return doctorRepo.findByName(filter);
    }

    @Override
    public void delete(Doctor doctor) {
        doctorRepo.delete(doctor);
    }

    @Override
    public int amountDoctors() {
        return (int) doctorRepo.count();
    }

    @Override
    public int amountPatients() {
        return (int) patientRepo.count();
    }

    @Override
    public int amountRecipes() {
        return (int) recipeRepo.count();
    }
}

package com.lessonprojectwithvaadin.demo.service.interfaces;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.dao.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe getOneById(Long id);

    void save(Recipe recipe);

    void deleteById(Long id);

    void delete(Recipe recipe);

    List<Recipe> findByFilter(String description, String priority, String patient);

    List<Doctor> findAllDoctors();

    List<Patient> findAllPatients();

    void saveRecipe(Recipe recipe);

    void deleteRecipe(Recipe recipe);
}

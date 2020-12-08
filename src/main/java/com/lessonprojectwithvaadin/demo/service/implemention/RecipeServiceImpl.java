package com.lessonprojectwithvaadin.demo.service.implemention;

import com.lessonprojectwithvaadin.demo.connectToDB.DBWork;
import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import com.lessonprojectwithvaadin.demo.dao.entity.Recipe;
import com.lessonprojectwithvaadin.demo.dao.repository.DoctorRepository;
import com.lessonprojectwithvaadin.demo.dao.repository.PatientRepository;
import com.lessonprojectwithvaadin.demo.dao.repository.RecipeRepository;
import com.lessonprojectwithvaadin.demo.service.interfaces.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private final RecipeRepository recipeRepo;

    @Autowired
    private final DoctorRepository doctorRepo;

    @Autowired
    private  final PatientRepository patientRepo;

    @Override
    public List<Recipe> findAll() {
        return recipeRepo.findAll();
    }

    @Override
    public Recipe getOneById(Long id) {
        Optional<Recipe> recipe = recipeRepo.findById(id);
        return recipe.get();
    }

    @Override
    public void save(Recipe recipe) {
        recipeRepo.save(recipe);
    }

    @Override
    public void deleteById(Long id) {
        recipeRepo.deleteById(id);
    }

    @Override
    public void delete(Recipe recipe) {
        recipeRepo.delete(recipe);
    }

    @Override
    public List<Recipe> findByFilter(String description, String priority,String patient) {
        return recipeRepo.findByDescription(description,priority,patient);
    }

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepo.findAll();
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepo.findAll();
    }

    @Override
    public void saveRecipe(Recipe recipe) {
        DBWork dbWork = new DBWork();
        System.out.println(recipe.toString());
        String query = "insert into recipe(date_create, description, priority, validity, doctor_id, patient_id)" +
                "values(?,?,?,?,?,?) ";
        try {
            PreparedStatement preparedStatement = dbWork.getConnection().prepareStatement(query);

            preparedStatement.setString(1,String.valueOf(recipe.getDate()));
            preparedStatement.setString(2,recipe.getDescription());
            preparedStatement.setString(3,recipe.getPriority().toString());
            preparedStatement.setString(4,String.valueOf(recipe.getValidity()));
            preparedStatement.setLong(5,recipe.getDoctor().getId());
            preparedStatement.setLong(6,recipe.getPatient().getId());
            preparedStatement.executeUpdate();
        }catch (Exception exception){
            System.out.println("Mistake executor: "+exception.getMessage());
        }
    }

    @Override
    public void deleteRecipe(Recipe recipe) {
        DBWork dbWork = new DBWork();
        String query = "delete from recipe where id=?";
        try {
            PreparedStatement preparedStatement = dbWork.getConnection().prepareStatement(query);
            preparedStatement.setLong(1,recipe.getId());
            preparedStatement.executeUpdate();
        }catch (Exception e){
            System.out.println("Mistake executor: "+e.getMessage());
        }
    }
}

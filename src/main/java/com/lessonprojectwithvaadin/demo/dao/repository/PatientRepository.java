package com.lessonprojectwithvaadin.demo.dao.repository;

import com.lessonprojectwithvaadin.demo.dao.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {

    @Query("from Patient p " +
            "where concat(p.fname,' ',p.lname,' ',p.patronymic) " +
            "like concat('%', :name ,'%')")
    List<Patient> findByName(@Param("name") String name);
}

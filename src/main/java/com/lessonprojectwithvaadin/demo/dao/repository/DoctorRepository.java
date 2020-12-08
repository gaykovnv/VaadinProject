package com.lessonprojectwithvaadin.demo.dao.repository;

import com.lessonprojectwithvaadin.demo.dao.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    @Query("from Doctor d " +
            "where concat(d.fname,' ',d.lname,' ',d.patronymic)" +
            "like concat('%', :name ,'%') ")
    List<Doctor> findByName(@Param("name") String name);
}

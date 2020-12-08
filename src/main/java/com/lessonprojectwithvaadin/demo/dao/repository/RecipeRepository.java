package com.lessonprojectwithvaadin.demo.dao.repository;

import com.lessonprojectwithvaadin.demo.dao.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    @Query("from Recipe r " +
            "where concat(r.description,' ') like concat('%',:description,'%') " +
            "and concat(r.priority,' ') like concat('%',:priority,'%') " +
            "and concat(r.patient.lname,' ') like concat('%',:patient,'%') ")
    List<Recipe> findByDescription(@Param("description") String description,
                                   @Param("priority") String priority,
                                   @Param("patient") String patient);

}

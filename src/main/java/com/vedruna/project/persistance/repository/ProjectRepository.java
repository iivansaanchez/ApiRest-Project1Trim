package com.vedruna.project.persistance.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.vedruna.project.persistance.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

    //Funcion para obtener el project por nombre
    Project findByName(String name);

    @Query("SELECT p FROM Project p JOIN p.listTechnologies t WHERE t.name = :techName")
    List<Project> findProjectsByTechnologyName(@Param("techName") String techName);
}
    


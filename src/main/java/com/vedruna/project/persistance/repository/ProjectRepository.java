package com.vedruna.project.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.project.persistance.models.Project;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer>{

    //Funcion para obtener el project por nombre
    Project findByName(String name);
    
}

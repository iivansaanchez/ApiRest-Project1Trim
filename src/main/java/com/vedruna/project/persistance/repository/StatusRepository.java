package com.vedruna.project.persistance.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.project.persistance.models.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>{
    //Metodo para obtener un estado por su nombre
    Optional<Status> findByName(String name);
    
}

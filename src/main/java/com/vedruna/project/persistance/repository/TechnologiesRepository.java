package com.vedruna.project.persistance.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.vedruna.project.persistance.models.Technologies;

@Repository
public interface TechnologiesRepository extends JpaRepository<Technologies, Integer>{
    
}

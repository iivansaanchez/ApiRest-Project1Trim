package com.vedruna.project.persistance.models;

import java.io.Serializable;
import java.util.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "technologies")
public class Technologies implements Serializable{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name="tech_id")
    private Integer id;
        
    @NotNull(message = "Technologies name cannot be null")
    @NotEmpty(message = "Technologies name cannot be empty")
    @Size(max = 30, message = "Technologies name must not exceed 30 characters")
    @Column(name="tech_name")
    private String name;

    @ManyToMany(mappedBy = "listTechnologies")
    private List<Project> listProjects = new ArrayList<>();
    
}

package com.vedruna.project.persistance.models;

import java.util.*;

import com.vedruna.project.validation.ValidUrl;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="developers")
public class Developers implements Serializable{

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "dev_id")
    private Integer id;

    @Column(name = "dev_name")
    @NotNull(message = "Developer name cannot be null")
    @NotEmpty(message = "Developer name cannot be empty")
    @Size(max = 50, message = "Developer name must not exceed 50 characters")
    private String name;

    @Column(name = "dev_surname")
    @NotNull(message = "Developer surname cannot be null")
    @NotEmpty(message = "Developer surname cannot be empty")
    @Size(max = 50, message = "Developer surname must not exceed 50 characters")
    private String surname;

    @Pattern(
    regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$",
    message = "Email must be a valid format")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    @Column(name="email")
    private String email;

    @ValidUrl
    @Column(name="linkedin_url")
    private String urlLinkedin;

    @ValidUrl
    @Column(name="github_url")
    private String urlGitHub;

    @ManyToMany(mappedBy = "listDevelopers")
    private List<Project> listProjects = new ArrayList<>();
    
}

package com.vedruna.project.persistance.models;

import java.util.*;

import java.io.Serializable;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
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
    private String name;

    @Column(name="dev_surname")
    private String surname;

    @Column(name="email")
    private String email;

    @Column(name="linkedin_url")
    private String urlLinkedin;

    @Column(name="github_url")
    private String urlGitHub;

    @ManyToMany(mappedBy = "listDevelopers")
    private List<Project> listProjects = new ArrayList<>();
    
}

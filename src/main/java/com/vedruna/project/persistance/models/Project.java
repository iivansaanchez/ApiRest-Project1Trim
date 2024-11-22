package com.vedruna.project.persistance.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="projects")
public class Project implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private Integer id;
    
    @Column(name="project_name")
    private String name;

    @Column(name="description")
    private String description;

    @Column(name="start_date")
    private Date startDate;

    @Column(name="end_date")
    private Date endDate;

    @Column(name="repository_url")
    private String repositoryUrl;

    @Column(name="demo_url")
    private String demoUrl;

    @Column(name="picture")
    private String picture;

    @ManyToOne
    @JoinColumn(name="status_status_id", nullable = true)
    private Status status;

    @ManyToMany
    @JoinTable(
        name="developers_worked_on_projects",
        joinColumns = {@JoinColumn(name="projects_project_id")},
        inverseJoinColumns = @JoinColumn(name="developers_dev_id")
    )
    private List<Developers> listDevelopers = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name="technologies_used_in_projects",
        joinColumns = {@JoinColumn(name="projects_project_id")},
        inverseJoinColumns = @JoinColumn(name="technologies_tech_id")

    )
    private List<Technologies> listTechnologies = new ArrayList<>();

}

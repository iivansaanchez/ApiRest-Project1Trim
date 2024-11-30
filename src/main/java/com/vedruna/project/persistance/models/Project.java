package com.vedruna.project.persistance.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.vedruna.project.validation.ValidUrl;

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
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
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
    
    /*
    Para las validaciones de esta clase vamos a usar las siguientes anotaciones que nos ofrece hibernate:
    - @NotNull --> Evita que el atributo sea nulo y en caso de serlo muestra un mensaje definido
    - @NotEmpty --> Evita que el atributo este vacío en caso de serlo muestra un mensaje definido
    - @Size --> Este atributo se suele usar para las cadenas para que tener un control sobre sus caracteres
    - @Pattern --> En este caso usamos pattern para validar la url con una expresion regular que controlar,
        que la URL empiece por http, https o ftp, siga con :// y luego cualquier cosa
    */

    @NotNull(message = "Project name cannot be null")
    @NotEmpty(message = "Project name cannot be empty")
    @Size(max = 100, message = "Project name must not exceed 100 characters")
    @Column(name = "project_name")
    private String name;

    @Size(max = 255, message = "Description must not exceed 255 characters")
    @Column(name = "description")
    private String description;

    @PastOrPresent(message = "Start date must be past or in the today")
    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ValidUrl(message = "The repository URL is not valid. Please provide a valid URL starting with http, https, or ftp.")
    @Column(name = "repository_url")
    private String repositoryUrl;

    @ValidUrl(message = "The demo URL is not valid. Please provide a valid URL starting with http, https, or ftp.")
    @Column(name = "demo_url")
    private String demoUrl;

    @Column(name = "picture")
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

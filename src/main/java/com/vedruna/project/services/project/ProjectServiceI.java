package com.vedruna.project.services.project;


import java.util.List;

import org.springframework.data.domain.Page;


import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.persistance.models.Project;

public interface ProjectServiceI {
    
    //Aqui haremos todos las funciones necesarias con projects que son las siguientes:

    //Funcion obtener todos los projects pero paginados
    Page<ProjectDTO> findAllProject(Integer numPage, Integer pageSize);

    //Función para extrar un proyecto por una palabra contenida
    List<ProjectDTO> showProjectByName(String name);

    //Función para añadir project
    void saveProject(Project project);

    //Función editar project
    void updateProject(String id, Project project);

    //Función borrar project por su id
    void deleteProject(Integer id);

    //Funcion para pasar de develop a test
    ProjectDTO developAndTest(String id);

    //Funcion para pasar de test a prod
    ProjectDTO testAndProd(String id);

    //Funcion para añadir una tecnologia a un proyecto
    ProjectDTO addTechnologyToProject(String id, Integer technologyId);

    //Funcion para añadir un desarrollador a un proyecto
    ProjectDTO addDeveloperToProject(String id, Integer developerId);

    //Funcion para obtener los proyectos por una tecnologia
    List<ProjectDTO> getProjectByNameTechnologies(String technology);

}

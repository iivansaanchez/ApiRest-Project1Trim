package com.vedruna.project.services.project;


import org.springframework.data.domain.Page;


import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.persistance.models.Project;

public interface ProjectServiceI {
    
    //Aqui haremos todos las funciones necesarias con projects que son las siguientes:

    //Funcion obtener todos los projects pero paginados
    Page<ProjectDTO> findAllProject(Integer numPage, Integer pageSize) throws Exception;

    //Función para extrar un proyecto por una palabra contenida
    Project showProjectByName(String name);

    //Función para añadir project
    void saveProject(Project project);

    //Función editar project
    void updateProject(Integer id, Project project);

    //Función borrar project por su id
    void deleteProduct(Integer id);
}

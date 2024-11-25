package com.vedruna.project.services.project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Project;
import com.vedruna.project.persistance.repository.ProjectRepository;

@Service
public class ProjectServiceImp implements ProjectServiceI {
    
    // Se utiliza ProjectRepository para realizar las operaciones básicas proporcionadas por JpaRepository.
    @Autowired
    ProjectRepository projectRepository;
    /**
     * Función para obtener todos projectos paginados
     * 
     * @return devuelve un conjunto de tipo Page con todos los projects
     */
    @Override
    public Page<ProjectDTO> findAllProject(Integer numPage, Integer pageSize){
        Pageable pageable = null;
    
        // Validación para que el número de página sea positivo
        if (numPage < 0) {
            throw new ExceptionValueNotRight("Number of page has to be positive");
        }
    
        // Validación para que el tamaño de página sea positivo
        if (pageSize <= 0) {
            throw new ExceptionValueNotRight("Size of page has to be positive");
        }
        //Añadimos al pageable los datos
        pageable = PageRequest.of(numPage, pageSize);
    

        Page<Project> pageOfProject = projectRepository.findAll(pageable);
        // Convertimos la página de Project en una página de ProjectDTO
        return pageOfProject.map(a -> new ProjectDTO(a));

    }    

    /**
     * Busca un proyecto por su nombre.
     * 
     * @param name Nombre del proyecto a buscar.
     * @return El objeto Project que coincide con el nombre dado, o null si no se encuentra.
     */
    @Override
    public List<ProjectDTO> showProjectByName(String word) {
        //Creamos una lista de proyectos DTO
        List<ProjectDTO> listProjectDTO = new ArrayList<>();
        //Obtenemos una lista de todos los projects
        List<Project> projectList = projectRepository.findAll();
        //Comprobamos que no esta vacio o es nulo
        if(projectList != null && !projectList.isEmpty()){
            //Recorremos la lista de proyectos y filtamos por la palabra
            for(Project p : projectList){
                //Hacemos un contains de la palabra al nombre de project
                if(p.getName().contains(word)){
                    //Lo pasamos a DTO
                    ProjectDTO projectDTO = new ProjectDTO(p);
                    //Lo añadimos a la lista de proyectos que contienen esa palabra en su nombre
                    listProjectDTO.add(projectDTO);
                }
            }
            //Una vez finalizado el bucle devolvemos la lista
            return listProjectDTO;
        }else{
            throw new ExceptionValueNotRight("There are no projects available.");
        }
    }

    /**
     * Guarda un nuevo proyecto en la base de datos.
     * 
     * @param project El objeto Project a guardar.
     */
    @Override
    public void saveProject(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void updateProject(Integer id, @Validated Project project) {
    // Validación del ID
    if (id == null || id <= 0) {
        throw new ExceptionValueNotRight("Invalid ID for project update");
    }

    // Buscar el proyecto existente por ID
    Project existingProject = projectRepository.findById(id)
        .orElseThrow(() -> new ExceptionElementNotFound(id)); // Si no se encuentra, lanzar una excepción

    // Actualizar solo los campos proporcionados (si no son null)
    if (project.getName() != null) {
        existingProject.setName(project.getName()); // Actualiza el nombre
    }
    if (project.getDescription() != null) {
        existingProject.setDescription(project.getDescription()); // Actualiza la descripción
    }
    if (project.getStartDate() != null) {
        existingProject.setStartDate(project.getStartDate()); // Actualiza la fecha de inicio
    }
    if (project.getEndDate() != null) {
        existingProject.setEndDate(project.getEndDate()); // Actualiza la fecha de fin
    }
    if (project.getRepositoryUrl() != null) {
        existingProject.setRepositoryUrl(project.getRepositoryUrl()); // Actualiza la URL del repositorio
    }
    if (project.getDemoUrl() != null) {
        existingProject.setDemoUrl(project.getDemoUrl()); // Actualiza la URL del demo
    }
    if (project.getPicture() != null) {
        existingProject.setPicture(project.getPicture()); // Actualiza la URL de la imagen
    }
    if (project.getStatus() != null) {
        existingProject.setStatus(project.getStatus()); // Actualiza el estado del proyecto
    }

    // Guardar los cambios en la base de datos
    projectRepository.save(existingProject); // Si no existe, se actualizará el proyecto
}


    /**
     * Elimina un proyecto de la base de datos por su ID.
     * 
     * @param id El ID del proyecto que se desea eliminar.
     */
    @Override
    public void deleteProduct(Integer id) {
        if (id == null || id <= 0) {
            throw new ExceptionValueNotRight("Invalid ID for project delete");
        }

        Project existingProject = projectRepository.findById(id)
        .orElseThrow(() -> new ExceptionElementNotFound(id));

        projectRepository.deleteById(existingProject.getId());
    }

}

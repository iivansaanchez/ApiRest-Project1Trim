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
import com.vedruna.project.persistance.models.Status;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Project;
import com.vedruna.project.persistance.repository.ProjectRepository;
import com.vedruna.project.persistance.repository.StatusRepository;

@Service
public class ProjectServiceImp implements ProjectServiceI {
    
    // Se utiliza ProjectRepository para realizar las operaciones básicas proporcionadas por JpaRepository.
    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StatusRepository statusRepository;
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
    public void updateProject(String id, @Validated Project project) {
        // Validación inicial de id
        if (id == null || id.trim().isEmpty()) {
            throw new ExceptionValueNotRight("Id cannot be null or empty");
        }
    
        int idValid;
        try {
            idValid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ExceptionValueNotRight("Id must be a valid integer");
        }
    
        // Validación de id negativo o cero
        if (idValid <= 0) {
            throw new ExceptionValueNotRight("Id cannot be negative or zero");
        }

    // Buscar el proyecto existente por ID
    Project existingProject = projectRepository.findById(idValid)
        .orElseThrow(() -> new ExceptionElementNotFound(idValid)); // Si no se encuentra, lanzar una excepción

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
    public void deleteProject(Integer id) {
        // Eliminar el proyecto si existe
        projectRepository.deleteById(id);
    }

    @Override
    public ProjectDTO developAndTest(String id) {
        // Validación inicial de id
        if (id == null || id.trim().isEmpty()) {
            throw new ExceptionValueNotRight("Id cannot be null or empty");
        }
    
        int idValid;
        try {
            idValid = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            throw new ExceptionValueNotRight("Id must be a valid integer");
        }
    
        // Validación de id negativo o cero
        if (idValid <= 0) {
            throw new ExceptionValueNotRight("Id cannot be negative or zero");
        }

        // Buscar el proyecto existente por ID
        Project existingProject = projectRepository.findById(idValid)
            .orElseThrow(() -> new ExceptionElementNotFound(idValid));

        // Buscar el estado de "Testing"
        Status testingStatus = statusRepository.findById(2)
            .orElseThrow(() -> new ExceptionValueNotRight("Testing status not found"));

        try {
            // Verificar si ya está en estado "Testing"
            if (existingProject.getStatus().getId() != 2) {
                // Cambiar el estado del proyecto a "Testing"
                existingProject.setStatus(testingStatus);

                // Guardar los cambios
                projectRepository.save(existingProject);

                // Devolver el DTO
                return new ProjectDTO(existingProject);
            } else {
                throw new ExceptionValueNotRight("Cannot move project to Testing. It is already in Testing.");
            }
        } catch (Exception e) {
            throw new ExceptionValueNotRight(
                "An unexpected error occurred while moving the project to Testing: " + e.getMessage()
            );
        }

    }

    @Override
    public ProjectDTO testAndProd(String id) {
               // Validación inicial de id
               if (id == null || id.trim().isEmpty()) {
                throw new ExceptionValueNotRight("Id cannot be null or empty");
            }
        
            int idValid;
            try {
                idValid = Integer.parseInt(id);
            } catch (NumberFormatException e) {
                throw new ExceptionValueNotRight("Id must be a valid integer");
            }
        
            // Validación de id negativo o cero
            if (idValid <= 0) {
                throw new ExceptionValueNotRight("Id cannot be negative or zero");
            }
    
            // Buscar el proyecto existente por ID
            Project existingProject = projectRepository.findById(idValid)
                .orElseThrow(() -> new ExceptionElementNotFound(idValid));
    
            // Buscar el estado de "Production"
            Status testingStatus = statusRepository.findById(3)
                .orElseThrow(() -> new ExceptionValueNotRight("Production status not found"));
    
            try {
                // Verificar si ya está en estado "Production"
                if (existingProject.getStatus().getId() != 3) {
                    // Cambiar el estado del proyecto a "Production"
                    existingProject.setStatus(testingStatus);
    
                    // Guardar los cambios
                    projectRepository.save(existingProject);
    
                    // Devolver el DTO
                    return new ProjectDTO(existingProject);
                } else {
                    throw new ExceptionValueNotRight("Cannot move project to Production. It is already in Production.");
                }
            } catch (Exception e) {
                throw new ExceptionValueNotRight(
                    "An unexpected error occurred while moving the project to Production: " + e.getMessage()
                );
            }
    }

}

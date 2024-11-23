package com.vedruna.project.services.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.vedruna.project.dto.ProjectDTO;
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
    public Page<ProjectDTO> findAllProject(Integer numPage, Integer pageSize) throws Exception {
        Pageable pageable = null;
    
        // Validación para que el número de página sea positivo
        if (numPage < 0) {
            throw new Exception("Number of page has to be positive");
        }
    
        // Validación para que el tamaño de página sea positivo
        if (pageSize <= 0) {
            throw new Exception("Size of page has to be positive");
        }
        //Añadimos al pageable los datos
        pageable = PageRequest.of(numPage, pageSize);
    
        // Intentamos obtener los proyectos paginados
        try {
            Page<Project> pageOfProject = projectRepository.findAll(pageable);
            // Convertimos la página de Project en una página de ProjectDTO
            return pageOfProject.map(a -> new ProjectDTO(a));
        } catch (Exception e) {
            throw new Exception("The field you are trying to sort by does not exist");
        }
    }    

    /**
     * Busca un proyecto por su nombre.
     * 
     * @param name Nombre del proyecto a buscar.
     * @return El objeto Project que coincide con el nombre dado, o null si no se encuentra.
     */
    @Override
    public Project showProjectByName(String name) {
        return projectRepository.findByName(name);
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

    /**
     * Actualiza un proyecto existente en la base de datos.
     * 
     * @param id      El ID del proyecto que se va a actualizar.
     * @param project El objeto Project con los datos actualizados.
     */
    @Override
    public void updateProject(Integer id, Project project) {
        project.setId(id);
        projectRepository.save(project);
    }

    /**
     * Elimina un proyecto de la base de datos por su ID.
     * 
     * @param id El ID del proyecto que se desea eliminar.
     */
    @Override
    public void deleteProduct(Integer id) {
        projectRepository.deleteById(id);
    }
}

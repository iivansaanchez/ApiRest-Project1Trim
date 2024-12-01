package com.vedruna.project.controllers;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.project.dto.DevelopersDTO;
import com.vedruna.project.dto.GenericDTO;
import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.dto.TechnologiesDTO;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionPageNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Project;
import com.vedruna.project.persistance.models.Status;
import com.vedruna.project.persistance.repository.ProjectRepository;
import com.vedruna.project.persistance.repository.StatusRepository;
import com.vedruna.project.services.project.ProjectServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class ProjectController {

    @Autowired
    ProjectServiceI projectServiceI;

    @Autowired
    ProjectRepository projectRepository;

    @Autowired
    StatusRepository statusRepository;
    
    @GetMapping("/projects")
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(
        @RequestParam(value = "page") Optional<Integer> page,
        @RequestParam(value = "size") Optional<Integer> size) {

        // Convertimos los parámetros y llamamos al servicio
        Page<ProjectDTO> pageProject = projectServiceI.findAllProject(
            page.orElse(1),
            size.orElse(3));
        //Si el objeto es por casualidad null controlamos el error
        if(pageProject == null){
            return ResponseEntity.notFound().build();
        //Si obtenemos un tamaño 0 de la pagina salta una exception de la pagina no existe
        }else if(pageProject.getContent().size() == 0){
            throw new ExceptionPageNotFound("Page doesn't exists");
        }else{
            return ResponseEntity.ok(pageProject); // Si hay contenido, devolvemos los datos
        }
 
    }

    @GetMapping("/projects/{word}")
    public ResponseEntity<List<ProjectDTO>> getProjectByName(@PathVariable String word){
        //Llamamos al metodo para obtener la lista de proyectos que contienen esa palabra en su nombre
        List<ProjectDTO> listProjectDTO = projectServiceI.showProjectByName(word);
        //Comprobamos que no este vacia o sea nula
        if(listProjectDTO!=null && !listProjectDTO.isEmpty()){
            //En el caso de que exista no este vacia y no sea nula lo devolvemos
            return ResponseEntity.ok(listProjectDTO);
        }else{
            throw new ExceptionValueNotRight("There are no projects available.");
        } 
    }

    @PostMapping("/projects")
    public ResponseEntity<GenericDTO<ProjectDTO>> createProject(@Valid @RequestBody Project project, BindingResult br){
	    if(br.hasErrors()) {
	    	throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
        //Una vez validado extraemos el primer estado de un proyecto para asignarselo por defecto
        //Tenemos que extraerlo de manera que sino existe el 1 mande una exception porque si es nulo o vacio se cae el servidor
        Status status = statusRepository.findById(1).orElseThrow(() -> new ExceptionValueNotRight("Status not found"));
        project.setStatus(status);

        //Ahora vamos a asingarle una fecha de inicio que se marcara por el dia de hoy
        project.setStartDate(new Date(System.currentTimeMillis()));
        try {
            // Persistimos el proyecto
            projectServiceI.saveProject(project);
        
            // Convertimos el proyecto guardado en un DTO
            ProjectDTO projectDTO = new ProjectDTO(project);
    
            //Creamos el GenericDTO
            GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Added successfully", projectDTO);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(genericDTO);
        } catch (Exception e) {
            // Si ocurre una excepción relacionada con valores no válidos
            throw new ExceptionValueNotRight("An unexpected error occurred while creating the project: " + e.getMessage());
        }
    }

    @PutMapping("/projects/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> updateProject(@PathVariable String id, @Valid @RequestBody Project project, BindingResult br){
        if(br.hasErrors()) {
	    	throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }

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
    
        // Verificar si el desarrollador existe
        Project existingProject = projectRepository.findById(idValid)
                .orElseThrow(() -> new ExceptionElementNotFound(idValid));
        try {
            project.setStatus(existingProject.getStatus());
            //Una vez validado lo pasamos a projectDTO
            ProjectDTO projectDTO = new ProjectDTO(project);
            //Creamos un GenericDTO
            GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Update successfully", projectDTO);
            //Updateamos el project
            projectServiceI.updateProject(id, project);
            //Por ultimo devolvemos en forma de respuesta el genericDTO
            return ResponseEntity.status(HttpStatus.CREATED).body(genericDTO);
        } catch (Exception e) {
            // Si ocurre una excepción relacionada con valores no válidos
            throw new ExceptionValueNotRight("An unexpected error occurred while updating the project: " + e.getMessage());
        }
    }

    @DeleteMapping("/projects/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> deleteProject(@PathVariable String id){
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

        //Verificar si la technologia existe
        Project existingProject = projectRepository.findById(idValid)
        .orElseThrow(() -> new ExceptionElementNotFound(idValid));

        //Creamos el DTO
        ProjectDTO projectDTO = new ProjectDTO(existingProject);

        //Creamos el generico
        GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Delete successfully", projectDTO);

        //Lo eliminamos una vez que ya se ha validado todo
        projectServiceI.deleteProject(idValid);

        //Devolvemos un mensaje de aceptado con el objeto que hemos borrado
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }

    @PatchMapping("/projects/totesting/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> developAndTest(@PathVariable String id){

        ProjectDTO projectDTO = projectServiceI.developAndTest(id);
        //Convertimos a Generic
        GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Updating successfully", projectDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }

    @PatchMapping("/projects/toprod/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> testAndProd(@PathVariable String id){

        ProjectDTO projectDTO = projectServiceI.testAndProd(id);
        //Convertimos a Generic
        GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Updating successfully", projectDTO);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }

    @PostMapping("/technologies/used/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> addTechnologyToProject(
            @PathVariable String id, @RequestBody TechnologiesDTO technologiesDTO) {
        ProjectDTO updatedProject = projectServiceI.addTechnologyToProject(id, technologiesDTO.getId());
        GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Updating successfully", updatedProject);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }

    @PostMapping("/developers/worked/{id}")
    public ResponseEntity<GenericDTO<ProjectDTO>> addDeveloperToProject(
            @PathVariable String id, @RequestBody DevelopersDTO developersDTO) {
        ProjectDTO updatedProject = projectServiceI.addDeveloperToProject(id, developersDTO.getId());
        GenericDTO<ProjectDTO> genericDTO = new GenericDTO<ProjectDTO>("Updating successfully", updatedProject);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }

    @GetMapping("/projects/tec/{tech}")
    public ResponseEntity<GenericDTO<List<ProjectDTO>>> getProjectsByTechnology(@PathVariable String tech) {
        List<ProjectDTO> projects = projectServiceI.getProjectByNameTechnologies(tech);
        return ResponseEntity.ok(new GenericDTO<>("Projects fetched successfully", projects));
    }
}

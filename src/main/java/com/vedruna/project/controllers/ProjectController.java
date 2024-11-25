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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.exception.ExceptionPageNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Project;
import com.vedruna.project.persistance.models.Status;
import com.vedruna.project.persistance.repository.StatusRepository;
import com.vedruna.project.services.project.ProjectServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class ProjectController {

    @Autowired
    ProjectServiceI projectServiceI;

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
    public ResponseEntity<ProjectDTO> createProject(@Valid @RequestBody Project project, BindingResult br){
	    if(br.hasErrors()) {
	    	throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
        //Una vez validado extraemos el primer estado de un proyecto para asignarselo por defecto
        //Tenemos que extraerlo de manera que sino existe el 1 mande una exception porque si es nulo o vacio se cae el servidor
        Status status = statusRepository.findById(1).orElseThrow(() -> new ExceptionValueNotRight("Status not found"));
        project.setStatus(status);

        //Ahora vamos a asingarle una fecha de inicio que se marcara por el dia de hoy
        project.setStartDate(new Date(System.currentTimeMillis()));

        // Persistimos el proyecto
        projectServiceI.saveProject(project);
    
        // Convertimos el proyecto guardado en un DTO
        ProjectDTO projectDTO = new ProjectDTO(project);
    
        // Retornamos la respuesta con un estado HTTP 201 (Created)
        return ResponseEntity.status(HttpStatus.CREATED).body(projectDTO);
    }

    @PutMapping("/projects/{id}")
    public void updateProject(@PathVariable Integer id, @Valid @RequestBody Project project){
        projectServiceI.updateProject(id, project);
    }

    @DeleteMapping("/projects/{id}")
    public void deleteProject(@PathVariable Integer id){
        projectServiceI.deleteProduct(id);
    }
}

package com.vedruna.project.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.project.dto.ProjectDTO;
import com.vedruna.project.services.project.ProjectServiceI;

@RequestMapping("api/v1")
@RestController
public class ProjectController {

    @Autowired
    ProjectServiceI projectServiceI;
    
    @GetMapping("/projects")
    public ResponseEntity<Page<ProjectDTO>> getAllProjects(
        @RequestParam(value = "page") Optional<Integer> page,
        @RequestParam(value = "size") Optional<Integer> size) {

        try {
            // Convertimos los parámetros y llamamos al servicio
            Page<ProjectDTO> pageProject = projectServiceI.findAllProject(
                page.orElse(1),
                size.orElse(3));

            // Comprobamos si se encontraron proyectos
            if (pageProject.isEmpty()) {
                return ResponseEntity.noContent().build(); // Si no hay contenido, devolvemos 204
            }

            return ResponseEntity.ok(pageProject); // Si hay contenido, devolvemos los datos

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Si ocurre un error, devolvemos 500
        }
    }
}

package com.vedruna.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.project.dto.GenericDTO;
import com.vedruna.project.dto.TechnologiesDTO;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Technologies;
import com.vedruna.project.persistance.repository.TechnologiesRepository;
import com.vedruna.project.services.technologies.TecnologiesServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class TechnologiesController {

    @Autowired
    TechnologiesRepository technologiesRepository;

    @Autowired
    TecnologiesServiceI tecnologiesServiceI;

    @PostMapping("/technologies")
    public ResponseEntity<GenericDTO<TechnologiesDTO>> createTechnologies(@Valid @RequestBody Technologies technologies, BindingResult br){
        if(br.hasErrors()) {
	        throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
        try {
            //Añadimos la nueva tecnologia a la base de datos
            tecnologiesServiceI.saveTechnologies(technologies);
            //Convertimos el proyecto a DTO para no trabajar directamente con el objeto
            TechnologiesDTO technologiesDTO = new TechnologiesDTO(technologies);
            //Creamos el GenericDTO
            GenericDTO<TechnologiesDTO> genericDTO = new GenericDTO<TechnologiesDTO>("Added successfully", technologiesDTO);

            return  ResponseEntity.status(HttpStatus.CREATED).body(genericDTO);
        } catch (Exception e) {
             // Si ocurre una excepción relacionada con valores no válidos
             throw new ExceptionValueNotRight("An unexpected error occurred while creating the technology: " + e.getMessage());
        }
    }

    @DeleteMapping("/technologies/{id}")
    public ResponseEntity<GenericDTO<TechnologiesDTO>> deleteTechnologies(@PathVariable String id){
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
        Technologies existingTechnologies = technologiesRepository.findById(idValid)
        .orElseThrow(() -> new ExceptionElementNotFound(idValid));

        //Creamos el DTO
        TechnologiesDTO technologiesDTO = new TechnologiesDTO(existingTechnologies);

        //Creamos el generico
        GenericDTO<TechnologiesDTO> genericDTO = new GenericDTO<TechnologiesDTO>("Delete successfully", technologiesDTO);

        //Lo eliminamos una vez que ya se ha validado todo
        tecnologiesServiceI.deleteTechnologies(idValid);

        //Devolvemos un mensaje de aceptado con el objeto que hemos borrado
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }
}

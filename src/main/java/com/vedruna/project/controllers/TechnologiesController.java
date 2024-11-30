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

import com.vedruna.project.dto.TechnologiesDTO;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Technologies;
import com.vedruna.project.services.technologies.TecnologiesServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class TechnologiesController {

    @Autowired
    TecnologiesServiceI tecnologiesServiceI;

    @PostMapping("/technologies")
    public ResponseEntity<TechnologiesDTO> createTechnologies(@Valid @RequestBody Technologies technologies, BindingResult br){
        if(br.hasErrors()) {
	        throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
        //Añadimos la nueva tecnologia a la base de datos
        tecnologiesServiceI.saveTechnologies(technologies);
        //Convertimos el proyecto a DTO para no trabajar directamente con el objeto
        TechnologiesDTO technologiesDTO = new TechnologiesDTO(technologies);
        //Devolvemos el status y la technologiesDTO
        return  ResponseEntity.status(HttpStatus.CREATED).body(technologiesDTO);
    }

    @DeleteMapping("/technologies/{id}")
    public void deleteTechnologies(@PathVariable String id){
        tecnologiesServiceI.deleteTechnologies(id);
    }
}

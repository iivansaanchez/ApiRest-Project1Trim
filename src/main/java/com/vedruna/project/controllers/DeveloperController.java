package com.vedruna.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vedruna.project.dto.DevelopersDTO;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Developers;
import com.vedruna.project.services.developers.DeveloperServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class DeveloperController {

    @Autowired
    DeveloperServiceI developerServiceI;

    @PostMapping("/developers")
    public ResponseEntity<GenericDTO<DevelopersDTO>> createDeveloper(@Valid @RequestBody Developers developers, BindingResult br){
        if(br.hasErrors()) {
	    	throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
        //Persistimos el developer
        try {
            developerServiceI.saveDeveloper(developers);
            //Sino da error convertimos el developer en DTO
            DevelopersDTO developersDTO = new DevelopersDTO(developers);
            //Por ultimo, devolvemos el DTO
            GenericDTO<DevelopersDTO> genericDTO = new GenericDTO<>("Added successfully", developersDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(genericDTO);
        } catch (Exception e) {
            // Si ocurre una excepción relacionada con valores no válidos
            throw new ExceptionValueNotRight("An unexpected error occurred while creating the developer: " + e.getMessage());
        }   
    }

    @DeleteMapping("/developers/{id}")
    public void deleteDeveloper(@PathVariable String id){
        developerServiceI.deleteDeveloper(id);
    }
}

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
import com.vedruna.project.dto.GenericDTO;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Developers;
import com.vedruna.project.persistance.repository.DevelopersRepository;
import com.vedruna.project.services.developers.DeveloperServiceI;

import jakarta.validation.Valid;

@RequestMapping("api/v1")
@RestController
public class DeveloperController {

    @Autowired
    DevelopersRepository developersRepository;

    @Autowired
    DeveloperServiceI developerServiceI;

    @PostMapping("/developers")
    public ResponseEntity<GenericDTO<DevelopersDTO>> createDeveloper(@Valid @RequestBody Developers developers, BindingResult br){
        if(br.hasErrors()) {
	    	throw new ExceptionValueNotRight(br.getAllErrors().get(0).getDefaultMessage());
	    }
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
    public ResponseEntity<GenericDTO<DevelopersDTO>> deleteDeveloper(@PathVariable String id){
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
        Developers existingDevelopers = developersRepository.findById(idValid)
                .orElseThrow(() -> new ExceptionElementNotFound(idValid));
        
        //Creamos objeto DevelopersDTO
        DevelopersDTO developersDTO = new DevelopersDTO(existingDevelopers);

        //Creamos el objeto Generico
        GenericDTO<DevelopersDTO> genericDTO = new GenericDTO<>("Drop successfully", developersDTO);

        //Eliminamos el desarrollador una vez ya validado
        developerServiceI.deleteDeveloper(idValid);
        
        //Devolvemos un mensaje de aceptado con el objeto que hemos borrado
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(genericDTO);
    }
}

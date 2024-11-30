package com.vedruna.project.services.technologies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Technologies;
import com.vedruna.project.persistance.repository.TechnologiesRepository;

@Service
public class TecnologiesServiceImp implements TecnologiesServiceI{

    @Autowired
    TechnologiesRepository technologiesRepository;

    @Override
    public void saveTechnologies(Technologies technologies) {
        technologiesRepository.save(technologies);
    }

    @Override
    public void deleteTechnologies(String id) {

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

        //Eliminar tecnologia si existe
        technologiesRepository.deleteById(existingTechnologies.getId());
    }
    
}

package com.vedruna.project.services.developers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;
import com.vedruna.project.persistance.models.Developers;
import com.vedruna.project.persistance.repository.DevelopersRepository;

@Service
public class DeveloperServiceImp implements DeveloperServiceI{

    @Autowired
    DevelopersRepository developersRepository;

    @Override
    public void saveDeveloper(Developers developers) {
        developersRepository.save(developers);
    }

    @Override
    public void deleteDeveloper(String id) {
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
    
        // Verificar si el proyecto existe
        Developers existingDevelopers = developersRepository.findById(idValid)
                .orElseThrow(() -> new ExceptionElementNotFound(idValid));

        //Eliminar el developer si existe
        developersRepository.deleteById(existingDevelopers.getId());
    }
    
}

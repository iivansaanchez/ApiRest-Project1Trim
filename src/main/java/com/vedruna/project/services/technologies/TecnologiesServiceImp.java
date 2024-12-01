package com.vedruna.project.services.technologies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public void deleteTechnologies(Integer id) {
        //Eliminar tecnologia si existe
        technologiesRepository.deleteById(id);
    }
    
}

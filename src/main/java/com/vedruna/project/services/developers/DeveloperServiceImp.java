package com.vedruna.project.services.developers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public void deleteDeveloper(Integer id) {
        //Eliminar el developer si existe
        developersRepository.deleteById(id);
    }
    
}

package com.vedruna.project.services.technologies;

import com.vedruna.project.persistance.models.Technologies;

public interface TecnologiesServiceI {

    //Funcion para añadir tecnologia
    void saveTechnologies(Technologies technologies);

    //Funcion para eliminar tecnologia
    void deleteTechnologies(String id);
    
} 

package com.vedruna.project.services.developers;

import com.vedruna.project.persistance.models.Developers;

public interface DeveloperServiceI {
    
    //Función para añadir un desarrollador a la BBDD
    void saveDeveloper(Developers developers);

    //Función para eliminar desarrolladro de la BBDD
    void deleteDeveloper(String id);
}

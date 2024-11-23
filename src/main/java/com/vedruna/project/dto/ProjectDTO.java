package com.vedruna.project.dto;

import java.util.ArrayList;
import java.util.List;

import com.vedruna.project.persistance.models.Developers;
import com.vedruna.project.persistance.models.Project;
import com.vedruna.project.persistance.models.Technologies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProjectDTO {

    private String nameProject;

    private String status;

    private List<DevelopersDTO> listDevelopers = new ArrayList<>();

    private List<TechnologiesDTO> listTechnologies = new ArrayList<>();

    public ProjectDTO(Project project){
        this.nameProject = project.getName();
        //Obtenemos el status
        this.status = project.getStatus().getName();

        //Convertimos la lista de Developers a DevelopersDTO
        for(Developers d : project.getListDevelopers()){
            //Creamos un DevelopersDTO apartir de cada Developer
            DevelopersDTO developersDTO = new DevelopersDTO(d);
            //Lo añadimos a la lista de DevelopersDTO
            this.listDevelopers.add(developersDTO);
        }

        //Ahora lo mismo pero con listTechonologies
        for(Technologies t : project.getListTechnologies()){
            TechnologiesDTO technologiesDTO = new TechnologiesDTO(t);
            this.listTechnologies.add(technologiesDTO);
        }
    }
    
}

package com.vedruna.project.dto;

import com.vedruna.project.persistance.models.Developers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DevelopersDTO {

    private Integer id;
    private String name;
    private String surname;

    //Con este contructor pasamos de developers a su DTO
    public DevelopersDTO (Developers developers){
        this.id = developers.getId();
        this.name = developers.getName();
        this.surname = developers.getSurname();
    }
    
}

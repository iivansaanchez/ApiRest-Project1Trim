package com.vedruna.project.dto;

import com.vedruna.project.persistance.models.Technologies;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TechnologiesDTO {

    private Integer id;
    private String name;

    //Con este constructor pasamos de una clase Technolgoies a su DTO
    public TechnologiesDTO(Technologies technologies){
        this.id = technologies.getId();
        this.name = technologies.getName();
    }
}

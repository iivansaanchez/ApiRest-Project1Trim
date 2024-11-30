package com.vedruna.project.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericDTO<T> {
    private String menssage;
    private T data;
}

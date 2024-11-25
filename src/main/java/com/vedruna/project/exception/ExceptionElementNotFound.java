package com.vedruna.project.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionElementNotFound extends RuntimeException{
	
	public ExceptionElementNotFound(Integer id) {
		super("Element with id: " + id + " not found in the database");
		}
}

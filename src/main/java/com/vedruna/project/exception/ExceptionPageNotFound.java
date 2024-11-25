package com.vedruna.project.exception;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExceptionPageNotFound extends RuntimeException{
	private static final long serialVersionUID = -409743470779314218L;

	public ExceptionPageNotFound(String  exception) {
		super(exception);
	}
}

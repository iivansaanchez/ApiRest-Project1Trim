package com.vedruna.project.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.vedruna.project.exception.ApiError;
import com.vedruna.project.exception.ExceptionElementNotFound;
import com.vedruna.project.exception.ExceptionPageNotFound;
import com.vedruna.project.exception.ExceptionValueNotRight;

@RestControllerAdvice
public class GlobalControllerError {
	
	@ExceptionHandler(ExceptionPageNotFound.class)
	public ResponseEntity<ApiError> handleExceptionPageNotFound(ExceptionPageNotFound e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
	
	@ExceptionHandler(ExceptionValueNotRight.class)
	public ResponseEntity<ApiError> handleExceptionValueNotRight(ExceptionValueNotRight e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
    
    @ExceptionHandler(ExceptionElementNotFound.class)
	public ResponseEntity<ApiError> handleExceptionElementNotFound(ExceptionElementNotFound e){
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, e.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
	}
}

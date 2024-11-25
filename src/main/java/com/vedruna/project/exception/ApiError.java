package com.vedruna.project.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Esta clase sirve para controlar los errores devolviendo estado fecha y mensaje de error
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ApiError {
    private HttpStatus status;
	@JsonFormat(shape= Shape.STRING, pattern ="dd/MM/yyyy hh:mm:ss")
	private LocalDateTime date;
	private String message;
	
	public ApiError(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
		this.date = LocalDateTime.now();
	} 
}

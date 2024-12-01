package com.vedruna.project.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UrlValidator.class) // Indicamos que esta anotación utiliza la clase UrlValidator para realizar la validación
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER}) // Especificamos dónde se puede usar: en atributos, métodos o parámetros
@Retention(RetentionPolicy.RUNTIME) // Hacemos que esta anotación esté disponible en tiempo de ejecución (importante para que funcione con Hibernate Validator)
public @interface ValidUrl {

    String message() default "Invalid URL format"; // Mensaje que se mostrará si la validación falla

    Class<?>[] groups() default {}; // Permite agrupar restricciones para validaciones complejas (no lo usamos aquí)

    Class<? extends Payload>[] payload() default {}; // Permite asociar información adicional para validaciones avanzadas
}


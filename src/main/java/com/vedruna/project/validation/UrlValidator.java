package com.vedruna.project.validation;
import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UrlValidator implements ConstraintValidator<ValidUrl, String> {

    // Patrón (regex) que define qué URLs son válidas
    private static final String URL_PATTERN = "^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+[/#?]?.*$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // Si el valor es null o está vacío, consideramos que es válido (puedes cambiar esta lógica si es necesario)
        if (value == null || value.isEmpty()) {
            return true;
        }
        // Comprobamos si el valor cumple con el patrón definido
        return Pattern.matches(URL_PATTERN, value);
    }
}

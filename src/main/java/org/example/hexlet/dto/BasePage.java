package org.example.hexlet.dto;

import io.javalin.validation.ValidationError;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class BasePage {
    private String flash;
    private Map<String, List<ValidationError<Object>>> errors;
}

package ru.vsu.cs.airTrafficControlSystem.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition (
        info = @Info(
               title = "Air Traffic Control System API",
                contact = @Contact (
                    name = "Dmitry Kulinchenko"
                )
        )
)
public class SwaggerConfig {
}

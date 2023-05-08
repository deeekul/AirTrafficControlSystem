package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AirCompanyDTO {
    @NotNull
    private String name;
}

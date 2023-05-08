package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AirportDTO {
    @NotNull
    private String name;

    @NotNull
    private String location;
}

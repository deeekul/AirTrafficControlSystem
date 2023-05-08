package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RunwayDTO {
    @NotNull
    private int number;

    @NotNull
    private AirportDTO airportDTO;
}

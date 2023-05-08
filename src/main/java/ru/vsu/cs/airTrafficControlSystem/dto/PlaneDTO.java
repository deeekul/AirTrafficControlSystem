package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.vsu.cs.airTrafficControlSystem.models.enums.PlaneType;

@Data
public class PlaneDTO {
    @NotNull
    @Enumerated(EnumType.STRING)
    private PlaneType type;

    @NotNull
    private String model;

    @NotNull
    private int capacity;
}

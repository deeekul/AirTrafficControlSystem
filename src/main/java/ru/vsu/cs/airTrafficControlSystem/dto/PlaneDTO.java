package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.vsu.cs.airTrafficControlSystem.models.enums.PlaneType;

@Data
public class PlaneDTO {
    @NotNull
    private PlaneType type;

    @NotNull
    private String model;

    @NotNull
    private int capacity;
}

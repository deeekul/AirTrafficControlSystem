package ru.vsu.cs.airTrafficControlSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.vsu.cs.airTrafficControlSystem.models.enums.PlaneType;

@Data
@Entity
@Table(name = "planes")
public class Plane {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private PlaneType type;

    @NotNull
    @Column(name="model")
    private String model;

    @NotNull
    @Column(name="capacity")
    private int capacity;
}

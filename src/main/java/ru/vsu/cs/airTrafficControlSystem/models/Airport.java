package ru.vsu.cs.airTrafficControlSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
@Entity
@Table(name = "airports")
public class Airport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name="name")
    private String name;

    @NotNull
    @Column(name = "location")
    private String location;
}

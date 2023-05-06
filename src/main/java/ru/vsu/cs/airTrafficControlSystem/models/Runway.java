package ru.vsu.cs.airTrafficControlSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name = "runways")
public class Runway {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "number")
    private int number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "airport_id", referencedColumnName = "id")
    private Airport airport;
}

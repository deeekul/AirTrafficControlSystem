package ru.vsu.cs.airTrafficControlSystem.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Entity
@Table(name="flights")
public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotNull
    @Column(name = "number")
    private String number;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "departure_airport_id", referencedColumnName = "id")
    private Airport departureAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "destination_airport_id", referencedColumnName = "id")
    private Airport destinationAirport;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "airCompany_id", referencedColumnName = "id")
    private AirCompany airCompany;
}

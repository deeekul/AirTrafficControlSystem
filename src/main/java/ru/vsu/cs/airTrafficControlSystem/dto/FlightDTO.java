package ru.vsu.cs.airTrafficControlSystem.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FlightDTO {
    @NotNull
    private String number;

    @NotNull
    private AirportDTO departureAirportDTO;

    @NotNull
    private AirportDTO destinationAirportDTO;

    @NotNull
    private AirCompanyDTO airCompanyDTO;
}

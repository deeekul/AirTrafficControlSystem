package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.airTrafficControlSystem.exceptions.FlightNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Flight;
import ru.vsu.cs.airTrafficControlSystem.repositories.FlightRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class FlightService {
    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    public List<Flight> getFlights() {
        return flightRepository.findAll();
    }

    public List<Flight> getFlightsByAirCompanyName(String airCompanyName) {
        return flightRepository.findFlightsByAirCompanyName(airCompanyName);
    }

    public Flight getFlightById(int id) {
        Optional<Flight> foundFlight = flightRepository.findById(id);
        return foundFlight.orElseThrow(FlightNotFoundException::new);
    }

    @Transactional
    public void addFlight(Flight flight) {
        flightRepository.save(flight);
    }

    @Transactional
    public void updateFlight(int id, Flight flight) {
        Optional<Flight> foundFlight = flightRepository.findById(id);
        if (foundFlight.isPresent()) {
            Flight newFlight = foundFlight.get();
            newFlight.setAirCompany(flight.getAirCompany());
            newFlight.setNumber(flight.getNumber());
            flight.setDepartureAirport(flight.getDepartureAirport());
            newFlight.setDestinationAirport(flight.getDestinationAirport());
            flightRepository.save(newFlight);
        }
    }

    @Transactional
    public void deleteFlight(int id) {
        flightRepository.deleteById(id);
    }
}

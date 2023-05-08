package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirportNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Airport;
import ru.vsu.cs.airTrafficControlSystem.repositories.AirportRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AirportService {
    private final AirportRepository airportRepository;

    @Autowired
    public AirportService(AirportRepository airportRepository) {
        this.airportRepository = airportRepository;
    }

    public List<Airport> getAirports() {
        return airportRepository.findAll();
    }

    public List<Airport> getAirportsByLocation(String location) {
        return airportRepository.findAirportsByLocation(location);
    }

    public Airport getAirportByNameAndLocation(String name, String location) {
        Optional<Airport> foundAirport = airportRepository.findFirstByNameAndAndLocation(name, location);
        return foundAirport.orElseThrow(AirportNotFoundException::new);
    }

    public Airport getAirportById(int id) {
        Optional<Airport> foundAirport = airportRepository.findById(id);
        return foundAirport.orElseThrow(AirportNotFoundException::new);
    }

    @Transactional
    public void addAirport(Airport airport) {
        airportRepository.save(airport);
    }

    @Transactional
    public void deleteAirport(int id) {
        airportRepository.deleteById(id);
    }

    @Transactional
    public void updateAirport(int id, Airport airport) {
        Optional<Airport> foundAirport = airportRepository.findById(id);
        if (foundAirport.isPresent()) {
            Airport newAirport = foundAirport.get();
            newAirport.setName(airport.getName());
            newAirport.setLocation(airport.getLocation());
            airportRepository.save(newAirport);
        }
    }
}

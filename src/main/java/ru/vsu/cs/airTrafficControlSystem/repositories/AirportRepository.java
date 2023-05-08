package ru.vsu.cs.airTrafficControlSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.airTrafficControlSystem.models.Airport;

import java.util.List;
import java.util.Optional;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
    List<Airport> findAirportsByLocation(String location);

}

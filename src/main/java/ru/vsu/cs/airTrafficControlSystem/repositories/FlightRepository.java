package ru.vsu.cs.airTrafficControlSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.airTrafficControlSystem.models.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Integer> {

}

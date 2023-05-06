package ru.vsu.cs.airTrafficControlSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.airTrafficControlSystem.models.Airport;

@Repository
public interface AirportRepository extends JpaRepository<Airport, Integer> {
}

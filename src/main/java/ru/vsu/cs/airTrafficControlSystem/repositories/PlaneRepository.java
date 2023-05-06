package ru.vsu.cs.airTrafficControlSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vsu.cs.airTrafficControlSystem.models.Plane;

import java.util.List;

@Repository
public interface PlaneRepository extends JpaRepository<Plane, Integer> {
    List<Plane> findPlanesByModel(String model);
}

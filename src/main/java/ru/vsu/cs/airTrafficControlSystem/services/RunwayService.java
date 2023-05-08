package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.airTrafficControlSystem.exceptions.RunwayNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Runway;
import ru.vsu.cs.airTrafficControlSystem.repositories.RunwayRepository;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class RunwayService {
    private final RunwayRepository runwayRepository;

    @Autowired
    public RunwayService(RunwayRepository runwayRepository) {
        this.runwayRepository = runwayRepository;
    }

    public List<Runway> getRunways() {
        return runwayRepository.findAll();
    }

    public Runway getRunwayById(int id) {
        Optional<Runway> foundRunway = runwayRepository.findById(id);
        return foundRunway.orElseThrow(RunwayNotFoundException::new);
    }

    @Transactional
    public void addRunway(Runway runway) {
        runwayRepository.save(runway);
    }

    @Transactional
    public void deleteRunway(int id) {
        runwayRepository.deleteById(id);
    }

    @Transactional
    public void updateRunway(int id, Runway runway) {
        Optional<Runway> foundRunway = runwayRepository.findById(id);
        if (foundRunway.isPresent()) {
            Runway newRunway = foundRunway.get();
            newRunway.setNumber(runway.getNumber());
            newRunway.setAirport(runway.getAirport());
            runwayRepository.save(newRunway);
        }
    }
}

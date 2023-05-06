package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.airTrafficControlSystem.exceptions.PlaneNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Plane;
import ru.vsu.cs.airTrafficControlSystem.repositories.PlaneRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PlaneService {
    private final PlaneRepository planeRepository;

    @Autowired
    public PlaneService(PlaneRepository planeRepository) {
        this.planeRepository = planeRepository;
    }

    public List<Plane> getPlanes() {
        return planeRepository.findAll();
    }

    public List<Plane> getPlanesByModel(String model) {
        return planeRepository.findPlanesByModel(model);
    }

    public Plane getPlaneById(int id) {
        Optional<Plane> foundPlane = planeRepository.findById(id);
        return foundPlane.orElseThrow(PlaneNotFoundException::new);
    }

    @Transactional
    public void deletePlane(int id) {
        planeRepository.deleteById(id);
    }

    @Transactional
    public void addPlane(Plane plane) {
        planeRepository.save(plane);
    }
}

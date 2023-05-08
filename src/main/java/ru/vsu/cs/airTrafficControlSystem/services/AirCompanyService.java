package ru.vsu.cs.airTrafficControlSystem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirCompanyNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.AirCompany;
import ru.vsu.cs.airTrafficControlSystem.repositories.AirCompanyRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AirCompanyService {
    private final AirCompanyRepository airCompanyRepository;

    @Autowired
    public AirCompanyService(AirCompanyRepository airCompanyRepository) {
        this.airCompanyRepository = airCompanyRepository;
    }

    public List<AirCompany> getAirCompanies() {
        return airCompanyRepository.findAll();
    }

    public AirCompany getAirCompanyById(int id) {
        Optional<AirCompany> foundAirCompany = airCompanyRepository.findById(id);
        return foundAirCompany.orElseThrow(AirCompanyNotFoundException::new);
    }

    public AirCompany getAirCompanyByName(String name) {
        Optional<AirCompany> foundAirCompany = airCompanyRepository.findByName(name);
        return foundAirCompany.orElseThrow(AirCompanyNotFoundException::new);
    }

    @Transactional
    public void addAirCompany(AirCompany airCompany) {
        airCompanyRepository.save(airCompany);
    }

    @Transactional
    public void updateAirCompany(int id, AirCompany airCompany) {
        Optional<AirCompany> foundAirCompany = airCompanyRepository.findById(id);
        if (foundAirCompany.isPresent()) {
            AirCompany newAirCompany = foundAirCompany.get();
            newAirCompany.setName(airCompany.getName());
            airCompanyRepository.save(newAirCompany);
        }
    }

    @Transactional
    public void deleteAirCompany(int id) {
        airCompanyRepository.deleteById(id);
    }
}

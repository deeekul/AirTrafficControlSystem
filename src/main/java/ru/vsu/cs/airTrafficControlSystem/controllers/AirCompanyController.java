package ru.vsu.cs.airTrafficControlSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.airTrafficControlSystem.dto.AirCompanyDTO;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirCompanyNotCreatedException;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirCompanyNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.AirCompany;
import ru.vsu.cs.airTrafficControlSystem.services.AirCompanyService;
import ru.vsu.cs.airTrafficControlSystem.util.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import static ru.vsu.cs.airTrafficControlSystem.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/air_companies")
@Tag(name = "AirCompany Controller", description = "Взаимодействие с авиакомпаниями")
public class AirCompanyController {
    private final AirCompanyService airCompanyService;
    private final ModelMapper modelMapper;

    @Autowired
    public AirCompanyController(AirCompanyService airCompanyService, ModelMapper modelMapper) {
        this.airCompanyService = airCompanyService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Получить авиакомпании")
    public List<AirCompanyDTO> getAirports() {
        return airCompanyService.getAirCompanies().stream().map(this::convertToAirCompanyDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поулчить авиакомпанию по id")
    public AirCompanyDTO getAirCompanyById(@PathVariable("id") int id) {
        return convertToAirCompanyDTO(airCompanyService.getAirCompanyById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создать авиакомпанию")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AirCompanyDTO airCompanyDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = returnErrorsToClient(bindingResult);
            throw new AirCompanyNotCreatedException(errorMsg);
        }
        airCompanyService.addAirCompany(convertToAirCompany(airCompanyDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить авиакомпанию")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        airCompanyService.deleteAirCompany(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Обновить авиакомпанию")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid AirCompanyDTO airCompanyDTO) {
        airCompanyService.updateAirCompany(id, convertToAirCompany(airCompanyDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AirCompanyNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Air company with such id wasn't found!", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AirCompanyNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private AirCompany convertToAirCompany(AirCompanyDTO airCompanyDTO) {
        return modelMapper.map(airCompanyDTO, AirCompany.class);
    }

    private AirCompanyDTO convertToAirCompanyDTO(AirCompany airCompany) {
        return modelMapper.map(airCompany, AirCompanyDTO.class);
    }
}

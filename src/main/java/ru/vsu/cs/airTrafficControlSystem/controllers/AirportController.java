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
import ru.vsu.cs.airTrafficControlSystem.dto.AirportDTO;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirportNotCreatedException;
import ru.vsu.cs.airTrafficControlSystem.exceptions.AirportNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Airport;
import ru.vsu.cs.airTrafficControlSystem.services.AirportService;
import ru.vsu.cs.airTrafficControlSystem.util.ErrorResponse;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.vsu.cs.airTrafficControlSystem.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/airports")
@Tag(name = "Airport Controller", description = "Взаимодействие с аэропортами")
public class AirportController {
    private final AirportService airportService;
    private final ModelMapper modelMapper;

    @Autowired
    public AirportController(AirportService airportService, ModelMapper modelMapper) {
        this.airportService = airportService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Получить аэропорты (в теле можно указать location)")
    public List<AirportDTO> getAirports(@RequestParam(required = false) String location) {
        if (location != null) {
            return airportService.getAirportsByLocation(location).stream().map(this::convertToAirportDTO).collect(Collectors.toList());
        }
        return airportService.getAirports().stream().map(this::convertToAirportDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить аэропорт по id")
    public AirportDTO getAirportById(@PathVariable("id") int id) {
        return convertToAirportDTO(airportService.getAirportById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создать аэропорт")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AirportDTO airportDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = returnErrorsToClient(bindingResult);
            throw new AirportNotCreatedException(errorMsg);
        }
        airportService.addAirport(convertToAirport(airportDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить аэропорт")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        airportService.deleteAirport(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Обновить аэропорт")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid AirportDTO airportDTO) {
        airportService.updateAirport(id, convertToAirport(airportDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AirportNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Airport with such id wasn't found!", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(AirportNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Airport convertToAirport(AirportDTO airportDTO) {
        return modelMapper.map(airportDTO, Airport.class);
    }

    private AirportDTO convertToAirportDTO(Airport airport) {
        return modelMapper.map(airport, AirportDTO.class);
    }
}

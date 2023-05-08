package ru.vsu.cs.airTrafficControlSystem.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.airTrafficControlSystem.dto.FlightDTO;
import ru.vsu.cs.airTrafficControlSystem.exceptions.FlightNotCreatedException;
import ru.vsu.cs.airTrafficControlSystem.exceptions.FlightNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Flight;
import ru.vsu.cs.airTrafficControlSystem.services.FlightService;
import ru.vsu.cs.airTrafficControlSystem.util.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;

import static ru.vsu.cs.airTrafficControlSystem.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/flights")
@Tag(name = "Flight Controller", description = "Взаимодействие с рейсами")
public class FlightController {
    private final FlightService flightService;
    private final ModelMapper modelMapper;

    public FlightController(FlightService flightService, ModelMapper modelMapper) {
        this.flightService = flightService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Получить рейсы")
    public List<FlightDTO> getFlights() {
        return flightService.getFlights().stream().map(this::convertToFlightDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Поулчить рейс по id")
    public FlightDTO getFlightById(@PathVariable("id") int id) {
        return convertToFlightDTO(flightService.getFlightById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Создать рейс")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid FlightDTO flightDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String errorMsg = returnErrorsToClient(bindingResult);
            throw new FlightNotCreatedException(errorMsg);
        }
        flightService.addFlight(convertToFlight(flightDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить рейс")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        flightService.deleteFlight(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Обновить рейс")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody @Valid FlightDTO flightDTO) {
        flightService.updateFlight(id, convertToFlight(flightDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FlightNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "FLight with such id wasn't found!", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(FlightNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Flight convertToFlight(FlightDTO flightDTO) {
        return modelMapper.map(flightDTO, Flight.class);
    }

    private FlightDTO convertToFlightDTO(Flight flight) {
        return modelMapper.map(flight, FlightDTO.class);
    }
}

package ru.vsu.cs.airTrafficControlSystem.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.vsu.cs.airTrafficControlSystem.dto.PlaneDTO;
import ru.vsu.cs.airTrafficControlSystem.exceptions.PlaneNotCreatedException;
import ru.vsu.cs.airTrafficControlSystem.exceptions.PlaneNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Plane;
import ru.vsu.cs.airTrafficControlSystem.services.PlaneService;
import ru.vsu.cs.airTrafficControlSystem.util.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/planes")
public class PlaneController {
    private final PlaneService planeService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneController(PlaneService planeService, ModelMapper modelMapper) {
        this.planeService = planeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<PlaneDTO> getPlanes() {
        return planeService.getPlanes().stream().map(this::convertToPlaneDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public PlaneDTO getPlane(@PathVariable("id") int id) {
        return convertToPlaneDTO(planeService.getPlaneById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PlaneDTO planeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();
            for (FieldError error: fieldErrors) {
                errorMessage.append(error.getField())
                        .append(" - ").append(error.getDefaultMessage())
                        .append(";");
            }
            throw new PlaneNotCreatedException(errorMessage.toString());
        }
        planeService.addPlane(convertToPlane(planeDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        planeService.deletePlane(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    /*@PutMapping
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id) {

    }*/

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PlaneNotFoundException e) {
        ErrorResponse response = new ErrorResponse(
                "Plane with such id wasn't found!", System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorResponse> handleException(PlaneNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Plane convertToPlane(PlaneDTO planeDTO) {
        return modelMapper.map(planeDTO, Plane.class);
    }

    private PlaneDTO convertToPlaneDTO(Plane plane) {
        return modelMapper.map(plane, PlaneDTO.class);
    }
}

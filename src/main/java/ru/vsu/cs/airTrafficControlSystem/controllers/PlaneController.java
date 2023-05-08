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
import ru.vsu.cs.airTrafficControlSystem.dto.PlaneDTO;
import ru.vsu.cs.airTrafficControlSystem.exceptions.PlaneNotCreatedException;
import ru.vsu.cs.airTrafficControlSystem.exceptions.PlaneNotFoundException;
import ru.vsu.cs.airTrafficControlSystem.models.Plane;
import ru.vsu.cs.airTrafficControlSystem.services.PlaneService;
import ru.vsu.cs.airTrafficControlSystem.util.ErrorResponse;
import java.util.List;
import java.util.stream.Collectors;
import static ru.vsu.cs.airTrafficControlSystem.util.ErrorsUtil.returnErrorsToClient;

@RestController
@RequestMapping("/api/planes")
@Tag(name = "Plane Controller", description = "Взаимодействие с самолётами")
public class PlaneController {
    private final PlaneService planeService;
    private final ModelMapper modelMapper;

    @Autowired
    public PlaneController(PlaneService planeService, ModelMapper modelMapper) {
        this.planeService = planeService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Operation(summary = "Get all planes (в теле запроса можно указать model)")
    public List<PlaneDTO> getPlanes(@RequestParam(required = false) String model) {
        if (model != null) {
            return planeService.getPlanesByModel(model).stream().map(this::convertToPlaneDTO).collect(Collectors.toList());
        }
        return planeService.getPlanes().stream().map(this::convertToPlaneDTO).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get plane by id")
    public PlaneDTO getPlane(@PathVariable("id") int id) {
        return convertToPlaneDTO(planeService.getPlaneById(id));
    }

    @PostMapping("/create")
    @Operation(summary = "Create plane")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PlaneDTO planeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()){
            String errorMsg = returnErrorsToClient(bindingResult);
            throw new PlaneNotCreatedException(errorMsg);
        }
        planeService.addPlane(convertToPlane(planeDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Delete plane")
    public ResponseEntity<HttpStatus> delete(@PathVariable("id") int id) {
        planeService.deletePlane(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    @Operation(summary = "Update plane")
    public ResponseEntity<HttpStatus> update(@PathVariable("id") int id, @RequestBody PlaneDTO planeDTO) {
        planeService.updatePlane(id, convertToPlane(planeDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

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

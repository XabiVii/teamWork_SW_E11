package com.example.ecoembes.facade;

import java.time.LocalDate;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecoembes.dto.AssignRequestDto;
import com.example.ecoembes.dto.AssignResponseDto;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.service.AuthService;
import com.example.ecoembes.service.RecyclingPlantService;

import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/recyclingPlants")
public class RecyclingPlantController {

    private final RecyclingPlantService recyclingPlantService;
    private final AuthService authService;

    public RecyclingPlantController(RecyclingPlantService recyclingPlantService, AuthService authService) {
        this.authService = authService;
        this.recyclingPlantService = recyclingPlantService;
    }
    
    @GetMapping("/{plantName}/capacity")
    public ResponseEntity<Integer> getPlantCapacity(
            @RequestHeader("Token") String token,
            @PathVariable("plantName") String plantName,
            @RequestParam("date") @Parameter(description = "date (yyyy-MM-dd)", required = true, example = "2025-10-30") LocalDate date) {

        Employee employee = authService.getEmployeeByToken(token);

        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer remainingCapacity = recyclingPlantService.getRemainingCapacity(plantName, date);
        if (remainingCapacity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(remainingCapacity);
    }
    
    @PostMapping("/assignDumpster")
    public ResponseEntity<AssignResponseDto> assignDumpsterToPlant(
            @RequestBody AssignRequestDto request,
            @RequestHeader("Token") @Parameter(description = "Authorization token") String token) {

        Employee employee = authService.getEmployeeByToken(token);
        if (employee == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        try {
            	recyclingPlantService.assignDumpster(
            		request.getPlantName(),
            		request.getDumpsterIds(),
                    employee.getId()
            );

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}

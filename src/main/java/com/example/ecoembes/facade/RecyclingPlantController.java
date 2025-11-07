package com.example.ecoembes.facade;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ecoembes.entity.Employee;
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
    
    @GetMapping("/{plantId}/capacity")
    public ResponseEntity<Integer> getPlantCapacity(
            @RequestHeader("Token") String token,
            @PathVariable("plantId") Long plantId,
            @RequestParam("date") @Parameter(description = "date (yyyy-MM-dd)", required = true, example = "2025-10-30") LocalDate date) {

        Employee employee = authService.getEmployeeByToken(token);

        if (employee != null) {
            Integer remainingCapacity = recyclingPlantService.getRemainingCapacity(plantId, date);

            if (remainingCapacity == null) {
                return ResponseEntity.notFound().build();
            }

            return ResponseEntity.ok(remainingCapacity);
    	} else {
    		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    	}
    }
}

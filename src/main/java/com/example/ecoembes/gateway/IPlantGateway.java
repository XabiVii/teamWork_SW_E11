package com.example.ecoembes.gateway;

import java.time.LocalDate;

import com.example.ecoembes.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;

public interface IPlantGateway {
    RecyclingPlantDto getPlant();
    void assignDumpster(int totalDumpsters, int filling);
    Integer getRemainingCapacity(LocalDate date);
}

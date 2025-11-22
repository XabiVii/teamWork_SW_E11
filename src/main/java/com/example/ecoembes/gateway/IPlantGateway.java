package com.example.ecoembes.gateway;

import java.time.LocalDate;

import com.example.ecoembes.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;

public interface IPlantGateway {
    RecyclingPlantDto getPlant();
    AssignmentRecord assignDumpster(Long dumpsterId, Long employeeId, int filling);
    Integer getRemainingCapacity(LocalDate date);
}

package com.example.ecoembes.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;

public class AssignResponseDto {
    private Long plantId;
    private Long employeeId;
    private int dumpsterCount;
    private int totalContainers;
    private LocalDate date;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public int getDumpsterCount() {
        return dumpsterCount;
    }

    public void setDumpsterCount(int dumpsterCount) {
        this.dumpsterCount = dumpsterCount;
    }

    public int getTotalContainers() {
        return totalContainers;
    }

    public void setTotalContainers(int totalContainers) {
        this.totalContainers = totalContainers;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public static AssignResponseDto map(Long plantId, List<Dumpster> dumpsters, Employee employee, LocalDate date) {
        AssignResponseDto response = new AssignResponseDto();
        response.setPlantId(plantId);
        response.setEmployeeId(employee.getId());
        response.setDumpsterCount(dumpsters.size());
        response.setTotalContainers(dumpsters.stream().mapToInt(Dumpster::getCurrentFill).sum());
        response.setDate(date);
        return response;
    }
}

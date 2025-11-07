package com.example.ecoembes.dto;

import java.time.LocalDate;

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
}

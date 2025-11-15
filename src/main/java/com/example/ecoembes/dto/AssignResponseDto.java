package com.example.ecoembes.dto;

import java.time.LocalDate;
import java.util.List;

import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;

public class AssignResponseDto {
    private Long plantId;
    private Long dumpsterId;
    private Long employeeId;
    private LocalDate date;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public static AssignResponseDto map(Long plantId, Long dumpsterId, Employee employee, LocalDate date) {
        AssignResponseDto response = new AssignResponseDto();
        response.setPlantId(plantId);
        response.setDumpsterId(dumpsterId);
        response.setEmployeeId(employee.getId());
        response.setDate(date);
        return response;
    }
}

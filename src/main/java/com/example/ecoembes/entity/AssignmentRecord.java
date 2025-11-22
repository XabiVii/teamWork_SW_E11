package com.example.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(AssignmentRecordId.class)
public class AssignmentRecord {
	@Id
    private Long employeeId;
	@Id
    private String plantName;
	@Id
    private Long dumpsterId;

    private int totalContainers;

    private LocalDate date;

    public AssignmentRecord() {}

    public AssignmentRecord(Long employeeId, String plantName, Long dumpsterId, int totalContainers, LocalDate assignmentDate) {
        this.employeeId = employeeId;
        this.plantName = plantName;
        this.dumpsterId = dumpsterId;
        this.totalContainers = totalContainers;
        this.date = assignmentDate;
    }

    public Long getEmployeeId() { return employeeId; }
    public String getPlantName() { return plantName; }
    public Long getDumpsterId() { return dumpsterId; }
    public int getTotalContainers() { return totalContainers; }
    public LocalDate getDate() { return date; }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }
    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }
    public void setTotalContainers(int totalContainers) {
        this.totalContainers = totalContainers;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
}

package com.example.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(AssignmentRecordId.class)
public class AssignmentRecord {
	@Id
    private LocalDate date;
	@Id
    private String plantName;
	@Id
    private Long dumpsterId;

    private int totalContainers;

    private Long employeeId;

    @ManyToOne
    @JoinColumn(name = "dumpsterId", referencedColumnName = "id", insertable = false, updatable = false)
    private Dumpster dumpster;

    public AssignmentRecord() {}

    public AssignmentRecord(Long employeeId, String plantName, Long dumpsterId, int totalContainers, LocalDate date) {
        this.employeeId = employeeId;
        this.plantName = plantName;
        this.dumpsterId = dumpsterId;
        this.totalContainers = totalContainers;
        this.date = date;
    }

    public Long getEmployeeId() { return employeeId; }
    public void setEmployeeId(Long employeeId) { this.employeeId = employeeId; }

    public String getPlantName() { return plantName; }
    public void setPlantName(String plantName) { this.plantName = plantName; }

    public Long getDumpsterId() { return dumpsterId; }
    public void setDumpsterId(Long dumpsterId) { this.dumpsterId = dumpsterId; }

    public int getTotalContainers() { return totalContainers; }
    public void setTotalContainers(int totalContainers) { this.totalContainers = totalContainers; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public Dumpster getDumpster() { return dumpster; }
}

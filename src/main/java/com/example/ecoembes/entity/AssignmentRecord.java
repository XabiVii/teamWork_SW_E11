package com.example.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@IdClass(AssignmentRecordId.class)
public class AssignmentRecord {

	@Id
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

	@Id
    @ManyToOne
    @JoinColumn(name = "plant_id")
    private RecyclingPlant plant;

	@Id
    @ManyToOne
    @JoinColumn(name = "dumspter_id")
    private Dumpster dumpster;

    private int totalContainers;

    private LocalDate date;

    public AssignmentRecord() {}

    public AssignmentRecord(Employee employee, RecyclingPlant plant, Dumpster dumpster, int totalContainers, LocalDate assignmentDate) {
        this.employee = employee;
        this.plant = plant;
        this.dumpster = dumpster;
        this.totalContainers = totalContainers;
        this.date = assignmentDate;
    }

    public Employee getEmployee() { return employee; }
    public RecyclingPlant getPlant() { return plant; }
    public Dumpster getDumpsters() { return dumpster; }
    public int getTotalContainers() { return totalContainers; }
    public LocalDate getAssignmentDate() { return date; }
}

package com.example.ecoembes.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class AssignmentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "plant_id")
    private RecyclingPlant plant;

    @ManyToMany
    @JoinTable(
        name = "assignment_record_dumpsters",
        joinColumns = @JoinColumn(name = "assignment_record	_id"),
        inverseJoinColumns = @JoinColumn(name = "dumpster_id")
    )
    private List<Dumpster> dumpsters;

    private int totalContainers;

    private LocalDate assignmentDate;

    public AssignmentRecord() {}

    public AssignmentRecord(Employee employee, RecyclingPlant plant, List<Dumpster> dumpsters, int totalContainers, LocalDate assignmentDate) {
        this.timestamp = LocalDateTime.now();
        this.employee = employee;
        this.plant = plant;
        this.dumpsters = dumpsters;
        this.totalContainers = totalContainers;
        this.assignmentDate = assignmentDate;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public Employee getEmployee() { return employee; }
    public RecyclingPlant getPlant() { return plant; }
    public List<Dumpster> getDumpsters() { return dumpsters; }
    public int getTotalContainers() { return totalContainers; }
    public LocalDate getAssignmentDate() { return assignmentDate; }

    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
}

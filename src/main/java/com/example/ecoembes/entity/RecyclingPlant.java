package com.example.ecoembes.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecyclingPlant {

    @Id
    @Column(unique = true, nullable = false)
    private String name;

    private int postalCode;
    private String location;
    private int maxCapacity;
    private int currentFill;

    @OneToMany(mappedBy = "plantName", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AssignmentRecord> assignments = new ArrayList<>();

    public RecyclingPlant() { }

    public RecyclingPlant(String name, String address, int postalCode, int capacity) {
        this.name = name;
        this.location = address;
        this.postalCode = postalCode;
        this.maxCapacity = capacity;
        this.currentFill = 0;
    }

    public RecyclingPlant(String name, String address, int postalCode, int capacity, int currentFill) {
        this.name = name;
        this.location = address;
        this.postalCode = postalCode;
        this.maxCapacity = capacity;
        this.currentFill = currentFill;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCurrentFill() { return currentFill; }
    public void setCurrentFill(int currentFill) { this.currentFill = currentFill; }

    public List<AssignmentRecord> getAssignments() { return assignments; }
    public void setAssignments(List<AssignmentRecord> assignments) { this.assignments = assignments; }

    public void addAssignment(AssignmentRecord assignment) {
        assignments.add(assignment);
        currentFill += assignment.getDumpster().getCurrentFill();
    }

    public void removeAssignment(AssignmentRecord assignment) {
        if (assignments.remove(assignment)) {
            currentFill -= assignment.getDumpster().getCurrentFill();
        }
    }

    @Override
    public String toString() {
        return "RecyclingPlant{" +
                "name='" + name + '\'' +
                ", postalCode=" + postalCode +
                ", location='" + location + '\'' +
                ", maxCapacity=" + maxCapacity +
                ", currentFill=" + currentFill +
                ", assignments=" + assignments.size() +
                '}';
    }
}

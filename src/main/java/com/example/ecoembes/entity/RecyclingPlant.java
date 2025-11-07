package com.example.ecoembes.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class RecyclingPlant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int postalCode;
    private String address;
    private int capacity;
    private int currentFill;
    @OneToMany(mappedBy = "assignedPlant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Dumpster> assignedDumpsters = new ArrayList<>();

    public RecyclingPlant() { }

    public RecyclingPlant(Long id, String name, String address, int postalCode, int capacity) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.postalCode = postalCode;
        this.capacity = capacity;
        this.currentFill = 0;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public int getCurrentFill() { return currentFill; }
    public void setCurrentFill(int currentFill) { this.currentFill = currentFill; }

    public List<Dumpster> getAssignedDumpsters() { return assignedDumpsters; }

    public void setAssignedDumpsters(List<Dumpster> assignedDumpsters) {
        this.assignedDumpsters = assignedDumpsters;
        updateCurrentFill();
    }

    public void assignDumpster(Dumpster dumpster) {
        if (!assignedDumpsters.contains(dumpster)) {
            assignedDumpsters.add(dumpster);
            dumpster.setAssignedPlant(this);
            currentFill += dumpster.getCurrentFill();
        }
    }

    public void removeDumpster(Dumpster dumpster) {
        if (assignedDumpsters.remove(dumpster)) {
            currentFill -= dumpster.getCurrentFill();
            dumpster.setAssignedPlant(null);
        }
    }

    public void updateCurrentFill() {
        currentFill = assignedDumpsters.stream()
                .mapToInt(Dumpster::getCurrentFill)
                .sum();
    }

    @Override
    public String toString() {
        return "RecyclingPlant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", postalCode=" + postalCode +
                ", address='" + address + '\'' +
                ", capacity=" + capacity +
                ", currentFill=" + currentFill +
                ", assignedDumpsters=" + assignedDumpsters.size() +
                '}';
    }
}

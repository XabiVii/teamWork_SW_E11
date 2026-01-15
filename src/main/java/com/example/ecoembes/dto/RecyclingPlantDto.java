package com.example.ecoembes.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.RecyclingPlant;

public class RecyclingPlantDto {

    private String name;
    private String location;
    private int postalCode;
    private int maxCapacity;
    private int currentFill;
    private List<DumpsterDto> assignments = new ArrayList<>();

    public RecyclingPlantDto() {}

    public RecyclingPlantDto(String name, String location, int postalCode, int maxCapacity, int currentFill) {
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = currentFill;
    }

    public RecyclingPlantDto(String name, String location, int postalCode, int maxCapacity, int currentFill, List<DumpsterDto> assignments) {
        this.name = name;
        this.location = location;
        this.postalCode = postalCode;
        this.maxCapacity = maxCapacity;
        this.currentFill = currentFill;
        this.assignments = assignments;
    }

    // --- Getters / Setters ---
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public int getPostalCode() { return postalCode; }
    public void setPostalCode(int postalCode) { this.postalCode = postalCode; }

    public int getMaxCapacity() { return maxCapacity; }
    public void setMaxCapacity(int maxCapacity) { this.maxCapacity = maxCapacity; }

    public int getCurrentFill() { return currentFill; }
    public void setCurrentFill(int currentFill) { this.currentFill = currentFill; }

    public List<DumpsterDto> getAssignments() { return assignments; }
    public void addAssignment(DumpsterDto assignment) { this.assignments.add(assignment); }

    public RecyclingPlant map() {
        return new RecyclingPlant(
            this.getName(),
            this.getLocation(),
            this.getPostalCode(),
            this.getMaxCapacity(),
            this.getCurrentFill()
        );
    }

    public static RecyclingPlantDto map(RecyclingPlant plant) {
        return new RecyclingPlantDto(
            plant.getName(),
            plant.getLocation(),
            plant.getPostalCode(),
            plant.getMaxCapacity(),
            plant.getCurrentFill()
        );
    }

    public static List<RecyclingPlantDto> map(Map<RecyclingPlant, List<Dumpster>> plantToDumpsters) {
        List<RecyclingPlantDto> dtoList = new ArrayList<>();

        for (Map.Entry<RecyclingPlant, List<Dumpster>> entry : plantToDumpsters.entrySet()) {
            RecyclingPlant plant = entry.getKey();
            List<Dumpster> dumpsters = entry.getValue();

            List<DumpsterDto> dumpsterDtos = new ArrayList<>();
            for (Dumpster dumpster : dumpsters) {
                dumpsterDtos.add(DumpsterDto.map(dumpster));
            }

            RecyclingPlantDto dto = new RecyclingPlantDto(
                plant.getName(),
                plant.getLocation(),
                plant.getPostalCode(),
                plant.getMaxCapacity(),
                plant.getCurrentFill(),
                dumpsterDtos
            );

            dtoList.add(dto);
        }

        return dtoList;
    }
}

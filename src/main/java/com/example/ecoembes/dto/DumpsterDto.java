package com.example.ecoembes.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.RecyclingPlant;

public class DumpsterDto {

    private Long id;
    private String address;
    private int postalCode;
    private int capacity;
    private int currentFill;
    private String fillLevel;
    private RecyclingPlant assignedPlant;
    
    public DumpsterDto() {}

    public DumpsterDto(Long id, String address, int postalCode, int capacity, int currentFill, String fillLevel) {
        this.id = id;
        this.address = address;
        this.postalCode = postalCode;
        this.capacity = capacity;
        this.currentFill = currentFill;
        this.fillLevel = fillLevel;
    }

    public DumpsterDto(Long id, String address, int postalCode, int capacity, int currentFill, String fillLevel, RecyclingPlant assignedPlant) {
        this.id = id;
        this.address = address;
        this.postalCode = postalCode;
        this.capacity = capacity;
        this.currentFill = currentFill;
        this.fillLevel = fillLevel;
        this.assignedPlant = assignedPlant;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCurrentFill() {
        return currentFill;
    }

    public void setCurrentFill(int currentFill) {
        this.currentFill = currentFill;
    }

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        this.fillLevel = fillLevel;
    }

    public RecyclingPlant getAssignedPlant() {
        return assignedPlant;
    }

    public void setPlant(RecyclingPlant plant) {
        this.assignedPlant = plant;
    }

    public static DumpsterDto map(Dumpster dumpster, List<RecyclingPlant> plants) {
        AssignmentRecord todayAssignment = dumpster.getAssignments().stream()
                .filter(a -> a.getDate().isEqual(LocalDate.now()))
                .findFirst()
                .orElse(null);

        RecyclingPlant assignedPlant = null;

        if (todayAssignment != null) {
            assignedPlant = plants.stream()
                    .filter(p -> p.getName().equals(todayAssignment.getPlantName()))
                    .findFirst()
                    .orElse(null);
        }

        return new DumpsterDto(
                dumpster.getId(),
                dumpster.getAddress(),
                dumpster.getPostalCode(),
                dumpster.getCapacity(),
                dumpster.getCurrentFill(),
                dumpster.getFillLevel(),
                assignedPlant
        );
    }

    public static DumpsterDto map(Dumpster dumpster) {
    	
        return new DumpsterDto(
                dumpster.getId(),
                dumpster.getAddress(),
                dumpster.getPostalCode(),
                dumpster.getCapacity(),
                dumpster.getCurrentFill(),
                dumpster.getFillLevel()
        );
    }

    public Dumpster map() {
        return new Dumpster(
    	    this.id,
	        this.address,
	        this.postalCode,
	        this.capacity,
	        this.currentFill
        );
    }

    public static List<Dumpster> mapToDomain(List<DumpsterDto> dumpstersDto) {
        List<Dumpster> dumpsters = new ArrayList<>();
        for (DumpsterDto dumpsterDto : dumpstersDto) {
            dumpsters.add(dumpsterDto.map());
        }
        return dumpsters;
    }

    public static List<DumpsterDto> map(List<Dumpster> dumpsters) {
        List<DumpsterDto> dumpstersDto = new ArrayList<>();
        for (Dumpster dumpster : dumpsters) {
            dumpstersDto.add(map(dumpster));
        }
        return dumpstersDto;
    }
    
    public static List<DumpsterDto> map(Map<Dumpster, RecyclingPlant> dumpsters) {
        List<DumpsterDto> dumpstersDto = new ArrayList<>();
        for (Map.Entry<Dumpster, RecyclingPlant> entry : dumpsters.entrySet()) {
            dumpstersDto.add(new DumpsterDto(
                    entry.getKey().getId(),
                    entry.getKey().getAddress(),
                    entry.getKey().getPostalCode(),
                    entry.getKey().getCapacity(),
                    entry.getKey().getCurrentFill(),
                    entry.getKey().getFillLevel(),
                    entry.getValue()
            ));
        }
        return dumpstersDto;
    }

}

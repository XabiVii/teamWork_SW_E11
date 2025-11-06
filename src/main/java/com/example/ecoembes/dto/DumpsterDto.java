package com.example.ecoembes.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.ecoembes.entity.Dumpster;

public class DumpsterDto {

    private Long id;
    private String location;
    private int postalCode;
    private int capacity;
    private int currentFill;
    private String fillLevel;

    public DumpsterDto(Long id, String location, int postalCode, int capacity, int currentFill, String fillLevel) {
        this.id = id;
        this.location = location;
        this.postalCode = postalCode;
        this.capacity = capacity;
        this.currentFill = currentFill;
        this.fillLevel = fillLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    public static DumpsterDto Map(Dumpster dumpster) {
        return new DumpsterDto(
                dumpster.getId(),
                dumpster.getLocation(),
                dumpster.getPostalCode(),
                dumpster.getCapacity(),
                dumpster.getCurrentFill(),
                dumpster.getFillLevel()
        );
    }

    public static List<DumpsterDto> Map(List<Dumpster> dumpsters) {
        List<DumpsterDto> dumpstersDto = new ArrayList<>();
        for (Dumpster dumpster : dumpsters) {
            dumpstersDto.add(Map(dumpster));
        }
        return dumpstersDto;
    }
}

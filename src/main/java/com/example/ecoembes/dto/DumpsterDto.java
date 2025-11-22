package com.example.ecoembes.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.ecoembes.entity.Dumpster;

public class DumpsterDto {

    private Long id;
    private String address;
    private int postalCode;
    private int capacity;
    private int currentFill;
    private String fillLevel;
    
    public DumpsterDto() {}

    public DumpsterDto(Long id, String address, int postalCode, int capacity, int currentFill, String fillLevel) {
        this.id = id;
        this.address = address;
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

    public static List<DumpsterDto> map(List<Dumpster> dumpsters) {
        List<DumpsterDto> dumpstersDto = new ArrayList<>();
        for (Dumpster dumpster : dumpsters) {
            dumpstersDto.add(map(dumpster));
        }
        return dumpstersDto;
    }
}

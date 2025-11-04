package com.example.ecoembes.entity;

public class RecyclingPlant {
    private Long id;
    private String name;
    private int postalCode;
    private String location;
    private int maxCapacity;
    private int currentCapacity;

    // Default constructor (nécessaire pour la désérialisation JSON)
    public RecyclingPlant() { }

    public RecyclingPlant(Long id, String name, int postalCode, String location, int maxCapacity, int currentCapacity) {
        this.id = id;
        this.name = name;
        this.postalCode = postalCode;
        this.location = location;
        this.maxCapacity = maxCapacity;
        this.currentCapacity = currentCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public int getCurrentCapacity() {
        return currentCapacity;
    }

    public void setCurrentCapacity(int currentCapacity) {
        this.currentCapacity = currentCapacity;
    }
}

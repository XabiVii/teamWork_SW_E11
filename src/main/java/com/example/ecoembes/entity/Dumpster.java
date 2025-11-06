package com.example.ecoembes.entity;

import jakarta.persistence.*;

@Entity
public class Dumpster {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String location;
    private int postalCode;
    private int capacity;
    private int currentFill;
    private String fillLevel;

    private int id;
    private String address;
    private String postalCode;
    private String fillLevel; // LOW, MEDIUM, HIGH
    private int estimatedNumCont;

    public Dumpster(Long id, String location, int postalCode, int capacity, int currentFill) {
        this.id = id;
        this.address = address;
        this.postalCode = postalCode;
        this.capacity = capacity;
        this.currentFill = currentFill;
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
        if (currentFill < 0 || currentFill > capacity) {
            throw new IllegalArgumentException("Current fill must be between 0 and capacity");
        }
        calculateFillLevel();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        this.fillLevel = fillLevel;
    }

    public int getEstimatedNumCont() {
        return estimatedNumCont;
    }

    public void setEstimatedNumCont(int estimatedNumCont) {
        this.estimatedNumCont = estimatedNumCont;
    }

    @Override
    public String toString() {
        return "Dumpster{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", fillLevel='" + fillLevel + '\'' +
                ", estimatedNumCont=" + estimatedNumCont +
                '}';
    }
    
    public void calculateFillLevel() {

        switch ((int) ((double) this.currentFill * 3 / this.capacity)) {
	        case 0 -> this.fillLevel = "GREEN";
	        case 1 -> this.fillLevel = "ORANGE";
	        case 2, 3 -> this.fillLevel = "RED";
        }
    }
}

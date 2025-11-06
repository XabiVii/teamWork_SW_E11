package com.example.ecoembes.entity;

public class Dumpster {

    private int id;
    private String address;
    private String postalCode;
    private String fillLevel; // LOW, MEDIUM, HIGH
    private int estimatedNumCont;

    public Dumpster() {
    }

    public Dumpster(int id, String address, String postalCode, String fillLevel, int estimatedNumCont) {
        this.id = id;
        this.address = address;
        this.postalCode = postalCode;
        this.fillLevel = fillLevel;
        this.estimatedNumCont = estimatedNumCont;
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
}

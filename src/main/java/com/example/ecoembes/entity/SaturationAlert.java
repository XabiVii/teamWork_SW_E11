package com.example.ecoembes.entity;

public class SaturationAlert {

    private String date;
    private String zone;
    private String saturationLevel; // LOW, MEDIUM, HIGH

    public SaturationAlert() {
    }

    public SaturationAlert(String date, String zone, String saturationLevel) {
        this.date = date;
        this.zone = zone;
        this.saturationLevel = saturationLevel;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSaturationLevel() {
        return saturationLevel;
    }

    public void setSaturationLevel(String saturationLevel) {
        this.saturationLevel = saturationLevel;
    }

    @Override
    public String toString() {
        return "Saturation{" +
                "date='" + date + '\'' +
                ", zone='" + zone + '\'' +
                ", saturationLevel='" + saturationLevel + '\'' +
                '}';
    }
}

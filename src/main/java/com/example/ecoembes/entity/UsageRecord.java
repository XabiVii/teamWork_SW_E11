package com.example.ecoembes.entity;

import jakarta.persistence.*;

@Entity
public class UsageRecord {

    @EmbeddedId
    private UsageRecordId id;

    private int estimatedNumCont;
    private String fillLevel;

    @ManyToOne
    @JoinColumn(name = "plant_id", nullable = true)
    private RecyclingPlant plant;

    public UsageRecord() {}

    public UsageRecord(Long dumpsterId, java.time.LocalDate date, int estimatedNumCont, String fillLevel) {
        this.id = new UsageRecordId(dumpsterId, date);
        this.estimatedNumCont = estimatedNumCont;
        this.fillLevel = fillLevel;
        this.plant = null;
    }

    public UsageRecord(Long dumpsterId, java.time.LocalDate date, int estimatedNumCont, String fillLevel, RecyclingPlant plant) {
        this.id = new UsageRecordId(dumpsterId, date);
        this.estimatedNumCont = estimatedNumCont;
        this.fillLevel = fillLevel;
        this.plant = plant;
    }

    public UsageRecordId getId() {
        return id;
    }

    public void setId(UsageRecordId id) {
        this.id = id;
    }

    public int getEstimatedNumCont() {
        return estimatedNumCont;
    }

    public void setEstimatedNumCont(int estimatedNumCont) {
        this.estimatedNumCont = estimatedNumCont;
    }

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        this.fillLevel = fillLevel;
    }

    public RecyclingPlant getPlant() {
        return plant;
    }

    public void setPlant(RecyclingPlant plant) {
        this.plant = plant;
    }
}

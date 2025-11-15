package com.example.ecoembes.entity;

import jakarta.persistence.*;

@Entity
public class UsageRecord {

    @EmbeddedId
    private UsageRecordId id;

    private int estimatedNumCont;
    private String fillLevel;

    public UsageRecord() {}

    public UsageRecord(Long dumpsterId, java.time.LocalDate date, int estimatedNumCont, String fillLevel) {
        this.id = new UsageRecordId(dumpsterId, date);
        this.estimatedNumCont = estimatedNumCont;
        this.fillLevel = fillLevel;
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
}

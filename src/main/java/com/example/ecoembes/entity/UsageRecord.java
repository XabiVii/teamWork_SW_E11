package com.example.ecoembes.entity;

public class UsageRecord {

    private int dumpsterId;
    private String date;
    private int estimatedNumCont;
    private String fillLevel;

    public UsageRecord() {
    }

    public UsageRecord(int dumpsterId, String date, int estimatedNumCont, String fillLevel) {
        this.dumpsterId = dumpsterId;
        this.date = date;
        this.estimatedNumCont = estimatedNumCont;
        this.fillLevel = fillLevel;
    }

    public int getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(int dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    @Override
    public String toString() {
        return "UsageRecord{" +
                "dumpsterId=" + dumpsterId +
                ", date='" + date + '\'' +
                ", estimatedNumCont=" + estimatedNumCont +
                ", fillLevel='" + fillLevel + '\'' +
                '}';
    }
}

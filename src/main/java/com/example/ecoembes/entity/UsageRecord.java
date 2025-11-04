package com.example.ecoembes.entity;

import java.sql.Date;

public class UsageRecord {
    private Dumpster dumpster;
    private Date date;
    private int containerUsed;
    private int fillLevel;

    // Default constructor (nécessaire pour la désérialisation JSON)
    public UsageRecord() { }

    public UsageRecord(Dumpster dumpster, Date date, int containerUsed, int fillLevel) {
        this.dumpster = dumpster;
        this.date = date;
        this.containerUsed = containerUsed;
        this.fillLevel = fillLevel;
    }

    public Dumpster getDumpster() {
        return dumpster;
    }

    public void setDumpster(Dumpster dumpster) {
        this.dumpster = dumpster;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getContainerUsed() {
        return containerUsed;
    }

    public void setContainerUsed(int containerUsed) {
        this.containerUsed = containerUsed;
    }

    public int getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(int fillLevel) {
        this.fillLevel = fillLevel;
    }
}

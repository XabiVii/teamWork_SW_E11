package com.example.ecoembes.entity;

import java.sql.Date;
import java.util.List;

public class SaturationAlert {
    private Date date;
    private List<String> affectedZones;

    // Default constructor (nécessaire pour la désérialisation JSON)
    public SaturationAlert() { }

    public SaturationAlert(Date date, List<String> affectedZones) {
        this.date = date;
        this.affectedZones = affectedZones;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<String> getAffectedZones() {
        return affectedZones;
    }

    public void setAffectedZones(List<String> affectedZones) {
        this.affectedZones = affectedZones;
    }
}

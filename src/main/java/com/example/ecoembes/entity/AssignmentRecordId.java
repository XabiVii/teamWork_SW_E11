package com.example.ecoembes.entity;

import java.time.LocalDate;

public class AssignmentRecordId {
    private String plantName;
    private Long dumpsterId;
    private LocalDate date;

    public AssignmentRecordId() {}
    public AssignmentRecordId(String plantName, Long dumpsterId, LocalDate date) {
        this.plantName = plantName;
        this.dumpsterId = dumpsterId;
        this.date = date;
    }
}

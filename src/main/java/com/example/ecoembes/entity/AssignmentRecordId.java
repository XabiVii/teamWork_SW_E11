package com.example.ecoembes.entity;

public class AssignmentRecordId {
    private Long employee;
    private Long plant;
    private Long dumpster;

    public AssignmentRecordId() {}
    public AssignmentRecordId(Long employee, Long plant, Long dumpster) {
        this.employee = employee;
        this.plant = plant;
        this.dumpster = dumpster;
    }
}

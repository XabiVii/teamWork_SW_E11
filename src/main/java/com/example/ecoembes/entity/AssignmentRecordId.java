package com.example.ecoembes.entity;

public class AssignmentRecordId {
    private Long employeeId;
    private String plantName;
    private Long dumpsterId;

    public AssignmentRecordId() {}
    public AssignmentRecordId(Long employeeId, String plantName, Long dumpsterId) {
        this.employeeId = employeeId;
        this.plantName = plantName;
        this.dumpsterId = dumpsterId;
    }
}

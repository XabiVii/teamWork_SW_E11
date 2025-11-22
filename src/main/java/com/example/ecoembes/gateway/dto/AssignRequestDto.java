package com.example.ecoembes.gateway.dto;

import com.example.ecoembes.entity.AssignmentRecord;

public class AssignRequestDto {

    private Long EmployeeId;
    private Long dumpsterId;
    private int filling;

    public AssignRequestDto() {}

    public AssignRequestDto(Long dumpsterId, Long EmployeeId, int filling) {
        this.EmployeeId = EmployeeId;
        this.dumpsterId = dumpsterId;
        this.filling = filling;
    }

    public Long getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(Long EmployeeId) {
        this.EmployeeId = EmployeeId;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }

    public static AssignmentRecord map(AssignRequestDto request) {
    	AssignmentRecord record = new AssignmentRecord();
    	record.setEmployeeId(request.getEmployeeId());
    	record.setDumpsterId(request.getDumpsterId());
    	record.setTotalContainers(request.getFilling());
        return record;
    }
}

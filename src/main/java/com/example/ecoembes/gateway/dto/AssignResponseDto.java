package com.example.ecoembes.gateway.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.RecyclingPlant;

public class AssignResponseDto {
    private Long dumpsterId;
    private Long employeeId;
    private LocalDate date;

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


    public static AssignmentRecord map(String plantName, Long dumpsterId, Long employeeId, LocalDate date) {
    	AssignmentRecord record = new AssignmentRecord();
    	record.setPlantName(plantName);
    	record.setDumpsterId(dumpsterId);
    	record.setEmployeeId(employeeId);
    	record.setDate(date);
        return record;
    }


    public static AssignResponseDto map(AssignmentRecord record) {
        AssignResponseDto response = new AssignResponseDto();
        response.setDumpsterId(record.getDumpsterId());
        response.setEmployeeId(record.getEmployeeId());
        response.setDate(record.getDate());
        return response;
    }


    public static List<AssignResponseDto> map(List<AssignmentRecord> records) {
    	List<AssignResponseDto> responses = new ArrayList<AssignResponseDto>();
    	for(AssignmentRecord record : records) {
    		responses.add(AssignResponseDto.map(record));
    	}
        return responses;
    }
}

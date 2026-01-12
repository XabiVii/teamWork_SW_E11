package com.example.ecoembes.dto;

import java.util.List;

public class AssignRequestDto {

    private String plantName;
    private List<Long> dumpsterIds;

    public AssignRequestDto() {}

    public AssignRequestDto(String plantName, List<Long> dumpsterId) {
        this.plantName = plantName;
        this.dumpsterIds = dumpsterId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public List<Long> getDumpsterIds() {
        return dumpsterIds;
    }

    public void setDumpsterIds(List<Long> dumpsterId) {
        this.dumpsterIds = dumpsterId;
    }
    
    
}

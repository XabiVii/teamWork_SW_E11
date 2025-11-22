package com.example.ecoembes.dto;

public class AssignRequestDto {

    private String plantName;
    private Long dumpsterId;

    public AssignRequestDto() {}

    public AssignRequestDto(String plantName, Long dumpsterId) {
        this.plantName = plantName;
        this.dumpsterId = dumpsterId;
    }

    public String getPlantName() {
        return plantName;
    }

    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }
}

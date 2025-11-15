package com.example.ecoembes.dto;

public class AssignRequestDto {

    private Long plantId;
    private Long dumpsterId;

    public AssignRequestDto() {}

    public AssignRequestDto(Long plantId, Long dumpsterId) {
        this.plantId = plantId;
        this.dumpsterId = dumpsterId;
    }

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }
}

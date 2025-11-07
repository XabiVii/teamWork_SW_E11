package com.example.ecoembes.dto;

import java.time.LocalDate;
import java.util.List;

public class AssignRequestDto {
    private Long plantId;
    private List<Long> dumpsterIds;

    public Long getPlantId() {
        return plantId;
    }

    public void setPlantId(Long plantId) {
        this.plantId = plantId;
    }

    public List<Long> getDumpsterIds() {
        return dumpsterIds;
    }

    public void setDumpsterIds(List<Long> dumpsterIds) {
        this.dumpsterIds = dumpsterIds;
    }
}

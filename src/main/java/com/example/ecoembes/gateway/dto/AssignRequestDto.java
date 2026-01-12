package com.example.ecoembes.gateway.dto;


public class AssignRequestDto {

    private int totalDumpster;
    private int filling;

    public AssignRequestDto() {}

    public AssignRequestDto(int totalDumpster, int filling) {
        this.totalDumpster = totalDumpster;
        this.filling = filling;
    }

    public int getTotalDumpster() {
        return totalDumpster;
    }

    public void setTotalDumpster(int totalDumpster) {
        this.totalDumpster = totalDumpster;
    }

    public int getFilling() {
        return filling;
    }

    public void setFilling(int filling) {
        this.filling = filling;
    }
}

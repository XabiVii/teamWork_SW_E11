package com.example.ecoembes.gateway;

import java.time.LocalDate;

import com.example.ecoembes.entity.RecyclingPlant;

public interface IPlantGateway {
    RecyclingPlant getPlant();
    void assignDumpster(int totalDumpsters, int filling);
    Integer getRemainingCapacity(LocalDate date);
}

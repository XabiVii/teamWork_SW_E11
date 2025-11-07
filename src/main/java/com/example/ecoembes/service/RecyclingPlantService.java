package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.ecoembes.dao.RecyclingPlantRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.UsageRecord;

@Service
public class RecyclingPlantService {

    private final RecyclingPlantRepository recyclingPlantRepository;
    private final UsageRecordRepository usageRecordRepository;

    public RecyclingPlantService(RecyclingPlantRepository recyclingPlantRepository,
                                 UsageRecordRepository usageRecordRepository) {
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.usageRecordRepository = usageRecordRepository;
    }


    public Integer getRemainingCapacity(Long plantId, LocalDate date) {
        RecyclingPlant plant = recyclingPlantRepository.findById(plantId).orElse(null);
        if (plant == null) {
            return null;
        }

        List<UsageRecord> records = usageRecordRepository.findByPlantIdAndIdDate(plantId, date);
        int usedCapacity = records.stream()
                                  .mapToInt(UsageRecord::getEstimatedNumCont)
                                  .sum();

        return plant.getCapacity() - usedCapacity;
    }
}

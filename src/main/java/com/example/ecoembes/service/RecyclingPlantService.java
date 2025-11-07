package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecoembes.dao.AssignmentRecordRepository;
import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.RecyclingPlantRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.UsageRecord;

@Service
public class RecyclingPlantService {

    private final RecyclingPlantRepository recyclingPlantRepository;
    private final DumpsterRepository dumpsterRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final AssignmentRecordRepository assignmentRecordRepository;

    public RecyclingPlantService(RecyclingPlantRepository recyclingPlantRepository,
    		DumpsterRepository dumpsterRepository,
            UsageRecordRepository usageRecordRepository,
            AssignmentRecordRepository assignmentRecordRepository) {
        this.recyclingPlantRepository = recyclingPlantRepository;
        this.dumpsterRepository = dumpsterRepository;
        this.usageRecordRepository = usageRecordRepository;
        this.assignmentRecordRepository = assignmentRecordRepository;
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

    @Transactional
    public List<Dumpster> assignDumpstersToPlant(Long plantId, List<Long> dumpsterIds, LocalDate date, Employee employee) {
        RecyclingPlant plant = recyclingPlantRepository.findById(plantId)
                .orElseThrow(() -> new IllegalArgumentException("Plant not found"));

        List<Dumpster> dumpsters = dumpsterRepository.findAllById(dumpsterIds);
        if (dumpsters.isEmpty()) {
            throw new IllegalArgumentException("No dumpsters found");
        }

        int totalContainers = dumpsters.stream().mapToInt(Dumpster::getCurrentFill).sum();

        if (plant.getCurrentFill() + totalContainers > plant.getCapacity()) {
            throw new IllegalArgumentException("Plant capacity exceeded");
        }

        for (Dumpster dumpster : dumpsters) {
            plant.assignDumpster(dumpster);
        }

        recyclingPlantRepository.save(plant);

        AssignmentRecord record = new AssignmentRecord(employee, plant, dumpsters, totalContainers, date);
        assignmentRecordRepository.save(record);

        return dumpsters;
    }
}

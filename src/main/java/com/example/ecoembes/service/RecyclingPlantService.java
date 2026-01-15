package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecoembes.dao.AssignmentRepository;
import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.gateway.IPlantGateway;
import com.example.ecoembes.gateway.PlantGatewayFactory;

@Service
public class RecyclingPlantService {

    private final DumpsterRepository dumpsterRepository;
    private final AssignmentRepository assignmentRepository;
    private final PlantGatewayFactory factory;

    public RecyclingPlantService(DumpsterRepository dumpsterRepository,
                                 AssignmentRepository assignmentRepository,
                                 PlantGatewayFactory factory) {
        this.dumpsterRepository = dumpsterRepository;
        this.assignmentRepository = assignmentRepository;
        this.factory = factory;
    }

    public RecyclingPlant getPlant(String name) {
        IPlantGateway gateway = factory.getGateway(name);
        return gateway.getPlant();
    }

    @Transactional
    public void assignDumpster(String name, List<Long> dumpsterIds, Long employeeId) {
        List<Dumpster> dumpsters = dumpsterRepository.findByIdIn(dumpsterIds);
        if (dumpsters.isEmpty()) {
            throw new RuntimeException("Dumpster not found");
        }
        for (Dumpster dumpster : dumpsters) {
            if (assignmentRepository.existsByPlantNameAndDumpsterIdAndDate(
                    name, dumpster.getId(), LocalDate.now())) {
                throw new IllegalStateException(
                        "The dumpster " + dumpster.getId() + " is already assign today"
                );
            }
            
            if (factory.getGateway(name).getRemainingCapacity(LocalDate.now()) - dumpster.getCapacity() <0) {
                throw new RuntimeException("not enough capacity");            	
            }
            
            assignmentRepository.save(new AssignmentRecord(employeeId, name, dumpster.getId(),
                    dumpster.getCapacity(), LocalDate.now()));
        }

        factory.getGateway(name).assignDumpster(dumpsters.size(),
                dumpsters.stream().mapToInt(Dumpster::getCapacity).sum());
    }

    public Integer getRemainingCapacity(String plantName, LocalDate date) {
        IPlantGateway gateway = factory.getGateway(plantName);
        if (gateway == null) {
            throw new RuntimeException("gateway not found");
        }
        return gateway.getRemainingCapacity(date);
    }
    
    public Map<RecyclingPlant, List<Dumpster>> getAvailablePlants() {
        LocalDate today = LocalDate.now();

        List<Dumpster> dumpsters = dumpsterRepository.findAll();

        List<RecyclingPlant> plants = factory.getAllGateways().entrySet().stream()
                .map(entry -> {
                    try {
                        return entry.getValue().getPlant();
                    } catch (Exception e) {
                        System.err.println("Gateway inaccessible: " + entry.getKey() + " -> " + e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toList();

        Map<RecyclingPlant, List<Dumpster>> plantToDumpsters = new HashMap<>();
        for (RecyclingPlant plant : plants) {
            plantToDumpsters.put(plant, new ArrayList<>());
        }

        List<AssignmentRecord> assignmentsToday = assignmentRepository.findAll().stream()
                .filter(a -> a.getDate().isEqual(today))
                .toList();

        for (AssignmentRecord assignment : assignmentsToday) {
        	RecyclingPlant plant = plants.stream()
        	        .filter(p -> p.getName().toLowerCase().equals(assignment.getPlantName().toLowerCase()))
        	        .findFirst()
        	        .orElse(null);

        	Dumpster dumpster = dumpsters.stream()
        	        .filter(d -> d.getId().equals(assignment.getDumpsterId()))
        	        .findFirst()
        	        .orElse(null);

            if (plant != null && dumpster != null) {
                plantToDumpsters.get(plant).add(dumpster);
            }
        }

        return plantToDumpsters;
    }





}

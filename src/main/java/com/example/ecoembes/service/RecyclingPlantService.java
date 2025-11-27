package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dto.AssignResponseDto;
import com.example.ecoembes.dto.RecyclingPlantDto;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.gateway.IPlantGateway;
import com.example.ecoembes.gateway.PlantGatewayFactory;

@Service
public class RecyclingPlantService {

	private final DumpsterRepository dumpsterRepository;
    private final PlantGatewayFactory factory;
	
	public RecyclingPlantService(DumpsterRepository dumpsterRepository,PlantGatewayFactory factory){
		this.dumpsterRepository = dumpsterRepository;
        this.factory = factory;
	}

    public RecyclingPlantDto getPlant(String name) {
        IPlantGateway gateway = factory.getGateway(name);
        return gateway.getPlant();
    }

    public AssignmentRecord assignDumpster(String name, Long dumpsterId, Long employeeId) {
    	Optional<Dumpster> dumpster = dumpsterRepository.findById(dumpsterId);
    	if (dumpster.isEmpty()) {
    		throw new RuntimeException("Dumpster with ID " + dumpsterId + " not found");
    	}
        IPlantGateway gateway = factory.getGateway(name);
        return gateway.assignDumpster(dumpsterId, employeeId, dumpster.get().getCapacity());
    }

    public Integer getRemainingCapacity(String plantName, LocalDate date) {
        IPlantGateway gateway = factory.getGateway(plantName);
        if (gateway == null) {
        	throw new RuntimeException("gateway not found");
        }
        return gateway.getRemainingCapacity(date);
    }
}

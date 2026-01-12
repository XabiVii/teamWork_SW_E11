package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecoembes.dao.AssignmentRepository;
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
	private final AssignmentRepository assignmentRepository;
    private final PlantGatewayFactory factory;
	
	public RecyclingPlantService(DumpsterRepository dumpsterRepository, AssignmentRepository assignmentRepository, PlantGatewayFactory factory){
		this.dumpsterRepository = dumpsterRepository;
		this.assignmentRepository = assignmentRepository;
        this.factory = factory;
	}

    public RecyclingPlantDto getPlant(String name) {
        IPlantGateway gateway = factory.getGateway(name);
        return gateway.getPlant();
    }

    @Transactional
    public void assignDumpster(String name, List<Long> dumpsterIds, Long employeeId) {
    	List<Dumpster> dumpsters = dumpsterRepository.findByIdIn(dumpsterIds);
    	if (dumpsters.isEmpty()) {
    		throw new RuntimeException("Dumpster not found");
    	}
    	for(Dumpster dumpster : dumpsters) {
    		if (assignmentRepository.existsByPlantNameAndDumpsterIdAndDate(
    		        name, dumpster.getId(), LocalDate.now())) {
    		    throw new IllegalStateException(
    		        "The dumpster " + dumpster.getId() + " is already assign today"
    		    );
    		}
        	assignmentRepository.save(new AssignmentRecord(employeeId, name, dumpster.getId(), dumpster.getCapacity(), LocalDate.now()));
    	}
    	
        factory.getGateway(name).assignDumpster(dumpsterIds.size(), dumpsters.stream()
                .mapToInt(Dumpster::getCapacity)
                .sum());
    }

    public Integer getRemainingCapacity(String plantName, LocalDate date) {
        IPlantGateway gateway = factory.getGateway(plantName);
        if (gateway == null) {
        	throw new RuntimeException("gateway not found");
        }
        return gateway.getRemainingCapacity(date);
    }
}

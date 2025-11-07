package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.UsageRecord;

@Service
public class DumpsterService {

    private final DumpsterRepository dumpsterRepository;
    private final UsageRecordRepository usageRecordRepository;

    public DumpsterService(DumpsterRepository repository, UsageRecordRepository usageRecordRepository) {
        this.dumpsterRepository = repository;
        this.usageRecordRepository = usageRecordRepository;
    }

    public List<Dumpster> getAllDumpsters() {
        return dumpsterRepository.findAll();
    }

    public Optional<Dumpster> getDumpsterById(Long id) {
        return dumpsterRepository.findById(id);
    }
    
    public List<UsageRecord> getUsageBetweenDates(Long dumpsterId, LocalDate startDate, LocalDate endDate){
    	
        return usageRecordRepository.findByIdDumpsterIdAndIdDateBetween(dumpsterId, startDate, endDate);
    }

    public Optional<Dumpster> updateDumpsterInfo(Long id, int currentFill) {
        return dumpsterRepository.findById(id).map(dumpster -> {
            dumpster.setCurrentFill(currentFill);
            dumpster.calculateFillLevel();
            return dumpsterRepository.save(dumpster);
        });
    }
    
    public Dumpster createDumpster(Dumpster dumpster){
    	return dumpsterRepository.save(dumpster);
    }
    
    public List<Dumpster> getDumpsterByPostalCodeAndDate(LocalDate date, String postalCode){
    	List<Dumpster> dumpsters = dumpsterRepository.findAll();
    	List<Dumpster> results = new ArrayList<Dumpster>();
    	
    	for(Dumpster dumpster : dumpsters) {
    		List<UsageRecord> records = usageRecordRepository.findByIdDate(date);
    		
    		UsageRecord latest = records.stream()
                    .max(Comparator.comparing(r -> r.getId().getDate()))
                    .orElse(null);

			int latestFill = (latest != null) ? latest.getEstimatedNumCont() : dumpster.getCurrentFill();
			String fillLevel = (latest != null) ? latest.getFillLevel() : dumpster.getFillLevel();
			Dumpster updatedDumpster = new Dumpster(
	                dumpster.getId(),
	                dumpster.getAddress(),
	                dumpster.getPostalCode(),
	                dumpster.getCapacity(),
	                latestFill
	        );
	        updatedDumpster.setCurrentFill(latestFill);
	        updatedDumpster.setFillLevel(fillLevel);

	        results.add(updatedDumpster);
    	}

	    return results;
    	
    }
}

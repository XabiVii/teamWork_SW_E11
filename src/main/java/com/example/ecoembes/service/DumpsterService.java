package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.UsageRecord;

@Service
public class DumpsterService {

    private final DumpsterRepository dumpsterRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final EmployeeRepository employeeRepository;
    private final RecyclingPlantService plantService;
    private final EmailService emailService;

    public DumpsterService(DumpsterRepository repository, UsageRecordRepository usageRecordRepository, EmployeeRepository employeeRepository, RecyclingPlantService plantService, EmailService emailService) {
        this.dumpsterRepository = repository;
        this.usageRecordRepository = usageRecordRepository;
        this.employeeRepository = employeeRepository;
        this.plantService = plantService;
        this.emailService = emailService;
    }

    public Map<Dumpster, RecyclingPlant> getAllDumpsters() {
        List<Dumpster> dumpsters = dumpsterRepository.findAll();
        List<RecyclingPlant> plants = new ArrayList<>(plantService.getAvailablePlants().keySet());

        Map<Dumpster, RecyclingPlant> dumpsterPlantMap = new LinkedHashMap<>();

        for (Dumpster dumpster : dumpsters) {
            AssignmentRecord todayAssignment = dumpster.getAssignments().stream()
                    .filter(a -> a.getDate().isEqual(LocalDate.now()))
                    .findFirst()
                    .orElse(null);

            if (todayAssignment != null) {
                RecyclingPlant assignedPlant = plants.stream()
                		.filter(p -> p.getName().toLowerCase().equals(todayAssignment.getPlantName().toLowerCase()))
                        .findFirst()
                        .orElse(null);

                if (assignedPlant != null) {
                    dumpsterPlantMap.put(dumpster, assignedPlant);
                }
                else {
                	dumpsterPlantMap.put(dumpster, null);
                }
            }
            else {
            	dumpsterPlantMap.put(dumpster, null);
            }
        }

        return dumpsterPlantMap;
    }



    public Optional<Dumpster> getDumpsterById(Long id) {
        return dumpsterRepository.findById(id);
    }
    
    public List<UsageRecord> getUsageBetweenDates(Long dumpsterId, LocalDate startDate, LocalDate endDate){
    	
        return usageRecordRepository.findByIdDumpsterIdAndIdDateBetween(dumpsterId, startDate, endDate);
    }

    public Optional<Dumpster> updateDumpsterInfo(Long id, int currentFill) {
        return dumpsterRepository.findById(id).map(dumpster -> {
            if (dumpster.getCapacity() < currentFill) {
                throw new RuntimeException("not enough capacity");
            }
            dumpster.setCurrentFill(currentFill);
            dumpster.calculateFillLevel();
            Dumpster savedDumpster =  dumpsterRepository.save(dumpster);
            UsageRecord record = new UsageRecord(
                    dumpster.getId(),
                    java.time.LocalDate.now(),
                    savedDumpster.getCurrentFill(),
                    savedDumpster.getFillLevel()
            );
            usageRecordRepository.save(record);

            return savedDumpster;
        });
    }
    
    public Dumpster createDumpster(Dumpster dumpster){
    	return dumpsterRepository.save(dumpster);
    }
    
    public List<Dumpster> getDumpsterByPostalCodeAndDate(LocalDate date, int postalCode) {
        List<Dumpster> dumpsters = dumpsterRepository.findByPostalCode(postalCode);
        List<Dumpster> results = new ArrayList<>();

        for (Dumpster dumpster : dumpsters) {
            Optional<UsageRecord> recordOpt = usageRecordRepository
                    .findByIdDumpsterIdAndIdDate(dumpster.getId(), date)
                    .stream()
                    .findFirst();

            int latestFill;

            if (recordOpt.isPresent()) {
                UsageRecord record = recordOpt.get();
                latestFill = record.getEstimatedNumCont();
            } else {
                latestFill = dumpster.getCurrentFill();
            }

            Dumpster updatedDumpster = new Dumpster(
                    dumpster.getId(),
                    dumpster.getAddress(),
                    dumpster.getPostalCode(),
                    dumpster.getCapacity(),
                    latestFill
            );
            updatedDumpster.setCurrentFill(latestFill);

            results.add(updatedDumpster);
        }

        return results;
    }

    
    @Transactional(readOnly = true)
    public void checkSaturationAndNotify() {
        long total = dumpsterRepository.count();
        if (total == 0) return;

        long orangeOrRedCount = dumpsterRepository.countOrangeOrRed();
        double percentage = (double) orangeOrRedCount / total * 100.0;

        if (percentage >= 80.0) {
        	List<Employee> recipients = employeeRepository.findAll();

            String body = String.format("""
            Warning !  
			%.2f%% of the dumpsters are currently full or nearly full (ORANGE/RED).  
			Please arrange a collection as soon as possible.
            """, percentage);

            List<String> emails = recipients.stream()
                    .map(Employee::getEmail)
                    .toList();
            
            for (String email : emails) {
                emailService.sendSimpleMessage(email, body);
            }

        }
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void scheduledSaturationCheck() {
        checkSaturationAndNotify();
    }
}

package com.example.ecoembes.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.UsageRecord;

@Service
public class DumpsterService {

    private final DumpsterRepository dumpsterRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public DumpsterService(DumpsterRepository repository, UsageRecordRepository usageRecordRepository, EmployeeRepository employeeRepository, EmailService emailService) {
        this.dumpsterRepository = repository;
        this.usageRecordRepository = usageRecordRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
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
            String fillLevel;

            if (recordOpt.isPresent()) {
                UsageRecord record = recordOpt.get();
                latestFill = record.getEstimatedNumCont();
                fillLevel = record.getFillLevel();
            } else {
                latestFill = dumpster.getCurrentFill();
                fillLevel = dumpster.getFillLevel();
            }

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

    
    @Transactional(readOnly = true)
    public void checkSaturationAndNotify() {
        long total = dumpsterRepository.count();
        if (total == 0) return;

        long orangeOrRedCount = dumpsterRepository.countOrangeOrRed();
        double percentage = (double) orangeOrRedCount / total * 100.0;

        if (percentage >= 80.0) {
        	List<Employee> recipients = employeeRepository.findAll();

            String subject = "Ecoembes Alert: High Dumpster Saturation";
            String body = String.format("""
                Attention!  
				%.2f%% of the dumpsters are currently full or nearly full (ORANGE/RED).  
				Please arrange a collection as soon as possible.
                """, percentage);

            List<String> emails = recipients.stream()
                    .map(Employee::getEmail)
                    .toList();
            
            emailService.sendEmail(subject, body, emails.toArray(new String[0]));
        }
    }

    // automatical verification every 3am
    @Scheduled(cron = "0 0 3 * * *")
    public void scheduledSaturationCheck() {
        checkSaturationAndNotify();
    }
}

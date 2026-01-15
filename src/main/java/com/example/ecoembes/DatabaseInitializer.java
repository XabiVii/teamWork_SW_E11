package com.example.ecoembes;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ecoembes.dao.AssignmentRepository;
import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.UsageRecord;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DumpsterRepository dumpsterRepository;
    private final EmployeeRepository employeeRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final AssignmentRepository assignmentRepository;

    public DatabaseInitializer(DumpsterRepository dumpsterRepository,
                               EmployeeRepository employeeRepository,
                               UsageRecordRepository usageRecordRepository,
                               AssignmentRepository assignmentRepository) {
        this.dumpsterRepository = dumpsterRepository;
        this.employeeRepository = employeeRepository;
        this.usageRecordRepository = usageRecordRepository;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public void run(String... args) {

    	RecyclingPlant r1 = new RecyclingPlant("Plassb", "Bilbao", 48010, 5000);
    	RecyclingPlant r2 = new RecyclingPlant("ContSocket", "Bilbao", 48010, 5000);

        Dumpster d1 = new Dumpster(null, "Calle Mayor 12, Bilbao", 48005, 1000, 750);
        Dumpster d2 = new Dumpster(null, "Avenida Sabino Arana 45, Bilbao", 48013, 1200, 300);
        Dumpster d3 = new Dumpster(null, "Calle Autonomía 200, Bilbao", 48012, 800, 800);
        Dumpster d4 = new Dumpster(null, "Calle Licenciado Poza 89, Bilbao", 48011, 900, 100);
        Dumpster d5 = new Dumpster(null, "Gran Vía 15, Bilbao", 48009, 1100, 950);
        dumpsterRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5));

        AssignmentRecord a1 = new AssignmentRecord(1L, r1.getName(), d4.getId(), 900, LocalDate.now().minusDays(3));
        AssignmentRecord a2 = new AssignmentRecord(1L, r1.getName(), d2.getId(), 1200, LocalDate.now());
        AssignmentRecord a3 = new AssignmentRecord(1L, r2.getName(), d1.getId(), 1000, LocalDate.now().minusDays(3));
        AssignmentRecord a4 = new AssignmentRecord(1L, r2.getName(), d5.getId(), 1100, LocalDate.now());
        assignmentRepository.saveAll(Arrays.asList(a1, a2, a3, a4));
        

        employeeRepository.saveAll(Arrays.asList(
            new Employee(null, "Ana López", "ana.lopez@ecoembes.com", "password123"),
            new Employee(null, "Carlos García", "carlos.garcia@ecoembes.com", "securePass")
        ));

        usageRecordRepository.saveAll(Arrays.asList(
            new UsageRecord(d1.getId(), LocalDate.parse("2025-11-01"), 45, "RED"),
            new UsageRecord(d1.getId(), LocalDate.parse("2025-11-02"), 50, "RED"),
            new UsageRecord(d2.getId(), LocalDate.parse("2025-11-01"), 25, "ORANGE"),
            new UsageRecord(d3.getId(), LocalDate.parse("2025-11-03"), 80, "RED"),
            new UsageRecord(d5.getId(), LocalDate.parse("2025-11-02"), 60, "RED"),
            new UsageRecord(d4.getId(), LocalDate.parse("2025-11-01"), 40, "ORANGE"),
            new UsageRecord(d4.getId(), LocalDate.parse("2025-11-02"), 55, "ORANGE"),
            new UsageRecord(d2.getId(), LocalDate.parse("2025-11-03"), 35, "ORANGE")
        ));
    }
}

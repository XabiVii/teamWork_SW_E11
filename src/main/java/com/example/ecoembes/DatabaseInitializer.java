package com.example.ecoembes;

import java.time.LocalDate;
import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.dao.UsageRecordRepository;
import com.example.ecoembes.dao.RecyclingPlantRepository;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;
import com.example.ecoembes.entity.RecyclingPlant;
import com.example.ecoembes.entity.UsageRecord;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DumpsterRepository dumpsterRepository;
    private final EmployeeRepository employeeRepository;
    private final UsageRecordRepository usageRecordRepository;
    private final RecyclingPlantRepository recyclingPlantRepository;

    public DatabaseInitializer(DumpsterRepository dumpsterRepository,
                               EmployeeRepository employeeRepository,
                               UsageRecordRepository usageRecordRepository,
                               RecyclingPlantRepository recyclingPlantRepository) {
        this.dumpsterRepository = dumpsterRepository;
        this.employeeRepository = employeeRepository;
        this.usageRecordRepository = usageRecordRepository;
        this.recyclingPlantRepository = recyclingPlantRepository;
    }

    @Override
    public void run(String... args) {
        RecyclingPlant r1 = new RecyclingPlant();
        r1.setName("PlasSB Ltd.");
        r1.setAddress("Polígono Industrial 5, Bilbao");
        r1.setPostalCode(48010);
        r1.setCapacity(5000);

        RecyclingPlant r2 = new RecyclingPlant();
        r2.setName("ContSocket Ltd.");
        r2.setAddress("Calle Industria 23, Bilbao");
        r2.setPostalCode(48012);
        r2.setCapacity(4000);

        recyclingPlantRepository.saveAll(Arrays.asList(r1, r2));

        Dumpster d1 = new Dumpster(null, "Calle Mayor 12, Bilbao", 48005, 1000, 750);
        d1.setAssignedPlant(r1);

        Dumpster d2 = new Dumpster(null, "Avenida Sabino Arana 45, Bilbao", 48013, 1200, 400);
        d2.setAssignedPlant(r2);

        Dumpster d3 = new Dumpster(null, "Calle Autonomía 200, Bilbao", 48012, 800, 800);
        d3.setAssignedPlant(r1);

        Dumpster d4 = new Dumpster(null, "Calle Licenciado Poza 89, Bilbao", 48011, 900, 150);

        Dumpster d5 = new Dumpster(null, "Gran Vía 15, Bilbao", 48009, 1100, 950);
        d5.setAssignedPlant(r2);

        dumpsterRepository.saveAll(Arrays.asList(d1, d2, d3, d4, d5));

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

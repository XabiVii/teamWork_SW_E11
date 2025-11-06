package com.example.ecoembes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.dao.EmployeeRepository;
import com.example.ecoembes.entity.Dumpster;
import com.example.ecoembes.entity.Employee;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DumpsterRepository dumpstyerRepository;
    private final EmployeeRepository employeeRepository;

    public DatabaseInitializer(DumpsterRepository dumpstyerRepository, EmployeeRepository employeeRepository) {
        this.dumpstyerRepository = dumpstyerRepository;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void run(String... args) {
    	dumpstyerRepository.save(new Dumpster(null, "Calle Mayor 12, Bilbao", 48005, 1000, 750));
    	dumpstyerRepository.save(new Dumpster(null, "Avenida Sabino Arana 45, Bilbao", 48013, 1200, 400));
    	dumpstyerRepository.save(new Dumpster(null, "Calle Autonomía 200, Bilbao", 48012, 800, 800));
    	dumpstyerRepository.save(new Dumpster(null, "Calle Licenciado Poza 89, Bilbao", 48011, 900, 150));
    	dumpstyerRepository.save(new Dumpster(null, "Gran Vía 15, Bilbao", 48009, 1100, 950));
    	dumpstyerRepository.save(new Dumpster(null, "Paseo del Arenal 5, Bilbao", 48003, 1000, 500));
    	dumpstyerRepository.save(new Dumpster(null, "Calle Rodríguez Arias 25, Bilbao", 48010, 700, 300));
    	dumpstyerRepository.save(new Dumpster(null, "Plaza Moyúa 1, Bilbao", 48009, 1500, 1200));
    	dumpstyerRepository.save(new Dumpster(null, "Calle Alameda Recalde 56, Bilbao", 48008, 950, 200));
    	dumpstyerRepository.save(new Dumpster(null, "Puente de Deusto, Bilbao", 48014, 2000, 1800));
        
        employeeRepository.save(new Employee(null, "Ana López", "ana.lopez@ecoembes.com", "password123"));
        employeeRepository.save(new Employee(null, "Carlos García", "carlos.garcia@ecoembes.com", "securePass"));
        employeeRepository.save(new Employee(null, "Lucía Fernández", "lucia.fernandez@ecoembes.com", "eco2025"));
        employeeRepository.save(new Employee(null, "Miguel Torres", "miguel.torres@ecoembes.com", "recycle!"));
        employeeRepository.save(new Employee(null, "Laura Pérez", "laura.perez@ecoembes.com", "greenEarth"));
    
    }
}

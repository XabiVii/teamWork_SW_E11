package com.example.ecoembes.repository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.ecoembes.entity.Dumpster;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DumpsterRepository repository;

    public DatabaseInitializer(DumpsterRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        repository.save(new Dumpster(null, "Calle Mayor 12, Bilbao", 48005, 1000, 750));
        repository.save(new Dumpster(null, "Avenida Sabino Arana 45, Bilbao", 48013, 1200, 400));
        repository.save(new Dumpster(null, "Calle Autonomía 200, Bilbao", 48012, 800, 800));
        repository.save(new Dumpster(null, "Calle Licenciado Poza 89, Bilbao", 48011, 900, 150));
        repository.save(new Dumpster(null, "Gran Vía 15, Bilbao", 48009, 1100, 950));
        repository.save(new Dumpster(null, "Paseo del Arenal 5, Bilbao", 48003, 1000, 500));
        repository.save(new Dumpster(null, "Calle Rodríguez Arias 25, Bilbao", 48010, 700, 300));
        repository.save(new Dumpster(null, "Plaza Moyúa 1, Bilbao", 48009, 1500, 1200));
        repository.save(new Dumpster(null, "Calle Alameda Recalde 56, Bilbao", 48008, 950, 200));
        repository.save(new Dumpster(null, "Puente de Deusto, Bilbao", 48014, 2000, 1800));
    }
}

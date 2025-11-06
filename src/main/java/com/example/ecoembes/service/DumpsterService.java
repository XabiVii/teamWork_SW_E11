package com.example.ecoembes.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.ecoembes.dao.DumpsterRepository;
import com.example.ecoembes.entity.Dumpster;

@Service
public class DumpsterService {

    private final DumpsterRepository repository;

    public DumpsterService(DumpsterRepository repository) {
        this.repository = repository;
    }

    public List<Dumpster> getAllDumpsters() {
        return repository.findAll();
    }

    public Optional<Dumpster> updateDumpsterInfo(Long id, int currentFill) {
        return repository.findById(id).map(dumpster -> {
            dumpster.setCurrentFill(currentFill);
            dumpster.calculateFillLevel();
            return repository.save(dumpster);
        });
    }
}

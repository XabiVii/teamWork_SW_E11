package com.example.ecoembes.dao;	

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.ecoembes.entity.Dumpster;

@Repository
public interface DumpsterRepository extends JpaRepository<Dumpster, Long> { 
    List<Dumpster> findById(int id);
    List<Dumpster> findByPostalCode(int postalCode);
    @Query("SELECT COUNT(d) FROM Dumpster d WHERE d.fillLevel = 'ORANGE' OR d.fillLevel = 'RED'")
    long countOrangeOrRed();
}

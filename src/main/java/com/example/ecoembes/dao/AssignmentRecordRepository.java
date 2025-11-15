package com.example.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.AssignmentRecordId;

public interface AssignmentRecordRepository extends JpaRepository<AssignmentRecord, AssignmentRecordId> {
    List<AssignmentRecord> findByPlantIdAndDate(Long plantId, LocalDate date);
}

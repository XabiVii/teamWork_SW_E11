package com.example.ecoembes.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.ecoembes.entity.AssignmentRecord;

public interface AssignmentRecordRepository extends JpaRepository<AssignmentRecord, Long> {
}

package com.example.ecoembes.dao;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.ecoembes.entity.AssignmentRecord;
import com.example.ecoembes.entity.AssignmentRecordId;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentRecord, AssignmentRecordId>  {
	boolean existsByPlantNameAndDumpsterIdAndDate(
		    String plantName,
		    Long dumpsterId,
		    LocalDate date
		);
}

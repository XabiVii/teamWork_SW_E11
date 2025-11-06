package com.example.ecoembes.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecoembes.entity.UsageRecord;
import com.example.ecoembes.entity.UsageRecordId;

public interface UsageRecordRepository  extends JpaRepository<UsageRecord, UsageRecordId> {

    List<UsageRecord> findByIdDumpsterIdAndIdDateBetween(
        Long dumpsterId,
        LocalDate startDate,
        LocalDate endDate
    );
}

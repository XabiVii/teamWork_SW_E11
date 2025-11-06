package com.example.ecoembes.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.example.ecoembes.entity.UsageRecord;

public class UsageRecordDto {

    private Long dumpsterId;
    private LocalDate date;
    private int estimatedNumCont;
    private String fillLevel;

    public UsageRecordDto(Long dumpsterId, LocalDate date, int estimatedNumCont, String fillLevel) {
        this.dumpsterId = dumpsterId;
        this.date = date;
        this.estimatedNumCont = estimatedNumCont;
        this.fillLevel = fillLevel;
    }

    public Long getDumpsterId() {
        return dumpsterId;
    }

    public void setDumpsterId(Long dumpsterId) {
        this.dumpsterId = dumpsterId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getEstimatedNumCont() {
        return estimatedNumCont;
    }

    public void setEstimatedNumCont(int estimatedNumCont) {
        this.estimatedNumCont = estimatedNumCont;
    }

    public String getFillLevel() {
        return fillLevel;
    }

    public void setFillLevel(String fillLevel) {
        this.fillLevel = fillLevel;
    }

    public static UsageRecordDto Map(UsageRecord usageRecord) {
        return new UsageRecordDto(
        		usageRecord.getId().getDumpsterId(),
        		usageRecord.getId().getDate(),
        		usageRecord.getEstimatedNumCont(),
        		usageRecord.getFillLevel()
        );
    }

    public static List<UsageRecordDto> Map(List<UsageRecord> usageRecords) {
        List<UsageRecordDto> usageRecordDto = new ArrayList<>();
        for (UsageRecord usageRecord : usageRecords) {
        	usageRecordDto.add(Map(usageRecord));
        }
        return usageRecordDto;
    }
    
    
}

package com.example.ecoembes.entity;

import java.io.Serializable;
import java.time.LocalDate;
import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class UsageRecordId implements Serializable {

    private Long dumpsterId;
    private LocalDate date;

    public UsageRecordId() {}

    public UsageRecordId(Long dumpsterId, LocalDate date) {
        this.dumpsterId = dumpsterId;
        this.date = date;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UsageRecordId)) return false;
        UsageRecordId that = (UsageRecordId) o;
        return Objects.equals(dumpsterId, that.dumpsterId) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dumpsterId, date);
    }
}

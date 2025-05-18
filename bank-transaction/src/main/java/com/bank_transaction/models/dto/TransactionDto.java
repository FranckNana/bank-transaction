package com.bank_transaction.models.dto;

import java.time.LocalDateTime;

public class TransactionDto {

    private String primaryId;
    private String secondaryId;
    private String eventType;
    private LocalDateTime eventDateTime;
    private int stepRank;
    private int eventRank;

    public TransactionDto() {
    }

    public String getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(String primaryId) {
        this.primaryId = primaryId;
    }

    public String getSecondaryId() {
        return secondaryId;
    }

    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public int getStepRank() {
        return stepRank;
    }

    public void setStepRank(int stepRank) {
        this.stepRank = stepRank;
    }

    public int getEventRank() {
        return eventRank;
    }

    public void setEventRank(int eventRank) {
        this.eventRank = eventRank;
    }
}

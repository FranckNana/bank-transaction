package com.bank_transaction.models.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
public class Transaction implements Serializable {

    @Id
    private String primaryId;
    private String secondaryId;

    private String eventType;
    private LocalDateTime eventDateTime;

    @ManyToOne
    private Referentiel reference;

    public Transaction() {
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

    public Referentiel getReference() {
        return reference;
    }

    public void setReference(Referentiel reference) {
        this.reference = reference;
    }
}

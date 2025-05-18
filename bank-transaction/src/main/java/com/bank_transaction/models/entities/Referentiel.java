package com.bank_transaction.models.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;

@Entity
public class Referentiel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stepCode;
    private int stepRank;
    private int eventRank;
    private String eventType;

    public Referentiel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStepCode() {
        return stepCode;
    }

    public void setStepCode(String stepCode) {
        this.stepCode = stepCode;
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

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}

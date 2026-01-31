package com.milmgt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AuditLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String actionType;
    private String assetName;
    private String details;
    private LocalDateTime timestamp;

    public AuditLog() {}

    public AuditLog(String actionType, String assetName, String details) {
        this.actionType = actionType;
        this.assetName = assetName;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getActionType() { return actionType; }
    public String getAssetName() { return assetName; }
    public String getDetails() { return details; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
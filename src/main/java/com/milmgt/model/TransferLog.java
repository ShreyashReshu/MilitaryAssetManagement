package com.milmgt.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class TransferLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assetName;
    private String sourceBase;
    private String destBase;
    private LocalDateTime timestamp;

    public TransferLog() {}

    public TransferLog(String assetName, String sourceBase, String destBase) {
        this.assetName = assetName;
        this.sourceBase = sourceBase;
        this.destBase = destBase;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getAssetName() { return assetName; }
    public String getSourceBase() { return sourceBase; }
    public String getDestBase() { return destBase; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
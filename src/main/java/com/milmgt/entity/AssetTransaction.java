package com.milmgt.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "asset_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    private String type;
    private LocalDateTime timestamp;
    private String initiatedBy;

    @ManyToOne
    private Base sourceBase;

    @ManyToOne
    private Base destBase;
}
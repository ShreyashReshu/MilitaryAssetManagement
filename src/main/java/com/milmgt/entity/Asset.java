package com.milmgt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "assets")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    
    @Column(unique = true)
    private String serialNumber;

    private String type; // WEAPON, VEHICLE, AMMO
    private String status; // ACTIVE, TRANSIT, EXPENDED

    @ManyToOne
    @JoinColumn(name = "current_base_id")
    private Base currentBase;
}
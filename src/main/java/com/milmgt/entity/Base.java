package com.milmgt.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Base {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    private String location;
    private String commanderName;
}
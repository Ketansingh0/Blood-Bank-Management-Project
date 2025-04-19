package com.bloodbank.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blood_inventory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String bloodGroup; // e.g., A+, O-, etc.

    @Column(nullable = false)
    private int quantity; // e.g., number of units

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Optional: You can remove this method since Lombok @Data already generates setters.
}

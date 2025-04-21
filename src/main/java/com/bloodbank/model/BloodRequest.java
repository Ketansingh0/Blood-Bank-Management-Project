package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "blood_requests")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BloodRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String bloodGroup;

    private int units;

    private String status; // PENDING, APPROVED, REJECTED

    private LocalDateTime requestDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setRequestedAt(LocalDateTime now) {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'setRequestedAt'");
    }
}

package com.bloodbank.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users") // 👈 Add this line to avoid using the reserved keyword 'user'
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private String role; // ROLE_ADMIN, ROLE_USER, etc.

    public boolean isEmpty() {
      // TODO Auto-generated method stub
      throw new UnsupportedOperationException("Unimplemented method 'isEmpty'");
    }
}

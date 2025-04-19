package com.bloodbank.repository;

import com.bloodbank.model.BloodInventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodInventoryRepository extends JpaRepository<BloodInventory, Long> {
    List<BloodInventory> findByBloodGroup(String bloodGroup);
}

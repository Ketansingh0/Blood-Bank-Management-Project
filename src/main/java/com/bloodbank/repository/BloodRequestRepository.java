package com.bloodbank.repository;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {
    List<BloodRequest> findByUser(User user);
}

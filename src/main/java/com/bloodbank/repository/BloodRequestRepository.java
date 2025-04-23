package com.bloodbank.repository;

import com.bloodbank.model.BloodRequest;
import com.bloodbank.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BloodRequestRepository extends JpaRepository<BloodRequest, Long> {

    List<BloodRequest> findByUser(User user);

    // 🔍 Advanced Search Method
    @Query("SELECT r FROM BloodRequest r " +
           "WHERE (:status IS NULL OR r.status = :status) " +
           "AND (:email IS NULL OR LOWER(r.user.email) LIKE LOWER(CONCAT('%', :email, '%'))) " +
           "AND (:bloodGroup IS NULL OR r.bloodGroup = :bloodGroup)")
    List<BloodRequest> searchRequests(@Param("status") String status,
                                      @Param("email") String email,
                                      @Param("bloodGroup") String bloodGroup);
}

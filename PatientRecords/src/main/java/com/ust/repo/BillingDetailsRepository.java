package com.ust.repo;

import com.ust.model.BillingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BillingDetailsRepository extends JpaRepository<BillingDetails, Long> {
    List<BillingDetails> findByPatientId(Long patientId);
}
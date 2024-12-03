package com.ust.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transactions")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "patient_id", nullable = false)
//    @JsonBackReference
//    private Patient patient;
    private LocalDateTime transactionDate;
    private String transactionType;
    private Double amount;
    private String paymentMethod;
    private String description;
    private String status;
    @ManyToOne(fetch = FetchType.EAGER)
    @JsonBackReference
    private BillingDetails billingDetails;
}

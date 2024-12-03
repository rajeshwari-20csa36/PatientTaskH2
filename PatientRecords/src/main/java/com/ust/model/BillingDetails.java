package com.ust.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "billing_details")


public class BillingDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonBackReference
    private Patient patient;
    private LocalDate billDate;
    private String serviceDescription;
    private Double serviceCharge;
    private Double medicationCharge;
    private Double consultationFee;
    private Double totalAmount;
    private Boolean isFullyPaid;
    private LocalDate dueDate;
    @OneToMany(mappedBy = "billingDetails", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Transaction> transactions;
}

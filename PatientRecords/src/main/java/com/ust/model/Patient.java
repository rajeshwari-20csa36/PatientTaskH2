package com.ust.model;

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
@Table(name = "patients")
//@JsonIgnoreProperties(ignoreUnknown = true)

public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private int age;
    private String gender;
    private LocalDate dateOfBirth;
    private String email;
//
//    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
//    @JsonManagedReference
//    private List<Transaction> transactions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<BillingDetails> billingDetails;
}
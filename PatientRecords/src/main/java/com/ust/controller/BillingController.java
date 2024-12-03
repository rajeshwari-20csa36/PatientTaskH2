package com.ust.controller;

import com.ust.model.BillingDetails;
import com.ust.model.Transaction;
import com.ust.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
@CrossOrigin
public class BillingController {

    private final BillingService billingService;

//    @GetMapping("/patient/{patientId}/bills")
//    public ResponseEntity<List<BillingDetails>> getPatientBillingHistory(@PathVariable Long patientId) {
//        return ResponseEntity.ok(billingService.getPatientBillingHistory(patientId));
//    }

//    @GetMapping("/billing/{billingId}/transactions")
//    public ResponseEntity<List<Transaction>> getTransactionsForBilling(@PathVariable Long billingId) {
//        return ResponseEntity.ok(billingService.getTransactionsForBilling(billingId));
//    }

    @PostMapping("/bills")
    public ResponseEntity<BillingDetails> createBill(@RequestBody BillingDetails billingDetails) {
        return ResponseEntity.ok(billingService.createBill(billingDetails));
    }

//    @PostMapping("/transactions")
//    public ResponseEntity<Transaction> recordTransaction(@RequestBody Transaction transaction) {
//        return ResponseEntity.ok(billingService.recordTransaction(transaction));
//    }
}
package com.ust.controller;

import com.ust.model.BillingDetails;
import com.ust.model.Transaction;
import com.ust.service.BillingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients/billing")
@RequiredArgsConstructor
@CrossOrigin
public class BillingController {

    private final BillingService billingService;

    @PostMapping
    public ResponseEntity<BillingDetails> createBillWithTransactions(@RequestBody BillingDetails billingDetails) {
        return ResponseEntity.ok(billingService.createBillWithTransactions(billingDetails));
    }

    @GetMapping
    public ResponseEntity<List<BillingDetails>> getAllBillingDetails() {
        return ResponseEntity.ok(billingService.getAllBillingDetails());
    }
<<<<<<< HEAD

    @GetMapping("/{id}")
    public ResponseEntity<BillingDetails> getBillingDetailsById(@PathVariable Long id) {
        return billingService.getBillingDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
=======
    @GetMapping("/bills")
    public ResponseEntity<List<BillingDetails>> getBills() {
        return ResponseEntity.ok(billingService.getBillingDetailHistory());
    }
    @PostMapping("/trans")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transaction) {
        return ResponseEntity.ok(billingService.createTransaction(transaction));
>>>>>>> 76af7680674cca4d71580672c9e761d5b2c35cfb
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<List<Transaction>> getTransactionsForBilling(@PathVariable Long id) {
        return ResponseEntity.ok(billingService.getTransactionsForBilling(id));
    }

<<<<<<< HEAD
    @PostMapping("/{id}/transactions")
    public ResponseEntity<BillingDetails> addTransactionToBilling(
            @PathVariable Long id,
            @RequestBody Transaction transaction
    ) {
        return ResponseEntity.ok(billingService.addTransactionToBilling(id, transaction));
    }
=======
    @PutMapping("/bills/{id}")
    public ResponseEntity<String> updateBill(@PathVariable Long id) {
        billingService.updateBill(id);
        return ResponseEntity.ok("Updated");
    }
//    @PostMapping("/transactions")
//    public ResponseEntity<Transaction> recordTransaction(@RequestBody Transaction transaction) {
//        return ResponseEntity.ok(billingService.recordTransaction(transaction));
//    }
>>>>>>> 76af7680674cca4d71580672c9e761d5b2c35cfb
}
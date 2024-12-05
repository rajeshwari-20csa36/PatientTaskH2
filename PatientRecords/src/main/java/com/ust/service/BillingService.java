package com.ust.service;

import com.ust.model.BillingDetails;
import com.ust.model.Transaction;
import com.ust.repo.BillingDetailsRepository;
import com.ust.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingDetailsRepository billingDetailsRepository;
    private final TransactionRepository transactionRepository;

    @Transactional
    public BillingDetails createBillWithTransactions(BillingDetails billing) {
        double totalAmount = billing.getServiceCharge()
                + billing.getConsultationFee()
                + billing.getMedicationCharge();
        billing.setTotalAmount(totalAmount);
        billing.setIsFullyPaid(false);
        BillingDetails savedBilling = billingDetailsRepository.save(billing);
        if (billing.getTransactions() != null && !billing.getTransactions().isEmpty()) {
            for (Transaction transaction : billing.getTransactions()) {
                transaction.setBillingDetails(savedBilling);
                transaction.setTransactionDate(LocalDateTime.now());
                updateBillingStatus(savedBilling, transaction);
            }
            transactionRepository.saveAll(billing.getTransactions());
        }

        return savedBilling;
    }

    private void updateBillingStatus(BillingDetails billing, Transaction transaction) {
        if (transaction.getAmount() >= billing.getTotalAmount()) {
            billing.setIsFullyPaid(true);
            transaction.setStatus("FULL_PAYMENT");
        } else {
            transaction.setStatus("PARTIAL_PAYMENT");
        }
    }

    public List<BillingDetails> getAllBillingDetails() {
        return billingDetailsRepository.findAll();
    }

    public Optional<BillingDetails> getBillingDetailsById(Long id) {
        return billingDetailsRepository.findById(id);
    }

    public List<Transaction> getTransactionsForBilling(Long billingId) {
        return transactionRepository.findByBillingDetailsId(billingId);
    }

    @Transactional
    public BillingDetails addTransactionToBilling(Long billingId, Transaction transaction) {
        BillingDetails billing = billingDetailsRepository.findById(billingId)
                .orElseThrow(() -> new RuntimeException("Billing not found"));

        transaction.setBillingDetails(billing);
        transaction.setTransactionDate(LocalDateTime.now());

        updateBillingStatus(billing, transaction);

        transactionRepository.save(transaction);
        return billingDetailsRepository.save(billing);
    }
}
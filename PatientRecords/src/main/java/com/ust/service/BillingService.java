//package com.ust.service;
//
//import com.ust.model.BillingDetails;
//import com.ust.model.Transaction;
//import com.ust.repo.BillingDetailsRepository;
//import com.ust.repo.TransactionRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class BillingService {
//
//    private final BillingDetailsRepository billingDetailsRepository;
//    private final TransactionRepository transactionRepository;
//
//
//    public List<BillingDetails> getPatientBillingHistory(Long patientId) {
//        return billingDetailsRepository.findByPatientId(patientId);
//    }
//
//
//    public List<Transaction> getTransactionsForBilling(Long billingId) {
//        return transactionRepository.findByBillingDetailsId(billingId);
//    }
//
//    public BillingDetails createBill(BillingDetails billingDetails) {
//
//        Double total = billingDetails.getServiceCharge() +
//                billingDetails.getMedicationCharge() +
//                billingDetails.getConsultationFee();
//        billingDetails.setTotalAmount(total);
//        return billingDetailsRepository.save(billingDetails);
//    }
//
//    public Transaction recordTransaction(Transaction transaction) {
//        return transactionRepository.save(transaction);
//    }
//}
package com.ust.service;

import com.ust.model.BillingDetails;
import com.ust.model.Transaction;
import com.ust.repo.BillingDetailsRepository;
import com.ust.repo.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillingService {
    private final BillingDetailsRepository billingDetailsRepository;
    private final TransactionRepository transactionRepository;


    public BillingDetails createBill(BillingDetails billing) {
        double bill = billing.getServiceCharge() + billing.getConsultationFee() + billing.getMedicationCharge();
        billing.setTotalAmount(bill);
        billing.setIsFullyPaid(false);
        return billingDetailsRepository.save(billing);
    }

    public List<BillingDetails> getBillingDetailHistory() {
        return billingDetailsRepository.findAll();
    }

    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory() {
        return transactionRepository.findAll();
    }

    public void updateBill(Long id) {
        Optional<BillingDetails> billingDetails = billingDetailsRepository.findById(id);
        if (billingDetails.isPresent()) {
            BillingDetails billing = billingDetails.get();
            Optional<Transaction> traction = transactionRepository.findById((long) transactionRepository.findAll().size());
            if (traction.isPresent()) {
                double update = billing.getTotalAmount() - traction.get().getAmount();
                billing.setTotalAmount(update);
            }
            if(billing.getTotalAmount()==0)
            {
                billing.setIsFullyPaid(true);
            }
            billingDetailsRepository.save(billing);
        }
    }
}
import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Transaction } from '../../model/transaction.model';
import { BillingDetails } from '../../model/BillingDetails.model';
import { BillingService } from '../../service/billing.service';
import { TransactionService } from '../../service/transaction.service';
import { PaymentMethod } from '../../model/enums/payment-method.enum';
import { TransactionType } from '../../model/enums/transaction-type.enum';

@Component({
  selector: 'app-bill',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './bill.component.html',
  styleUrls: ['./bill.component.css']
})
export class BillComponent implements OnInit {
  mode: string = 'create';
  billingForm: FormGroup;
  transactionForm: FormGroup;
  transactions: Transaction[] = [];
  billingDetails: BillingDetails | null = null;
  patientId: number | null = null;
  currentBillingId: number | null = null;
  remainingAmount: number = 0;


  paymentMethods = Object.values(PaymentMethod);
  transactionTypes = Object.values(TransactionType);

  constructor(
    private fb: FormBuilder,
    private billingService: BillingService,
    private transactionService: TransactionService,
    private router: Router,
    private route: ActivatedRoute
  ) {
   
    this.billingForm = this.fb.group({
      serviceDescription: ['', Validators.required],
      serviceCharge: [0, [Validators.required, Validators.min(0)]],
      medicationCharge: [0, [Validators.required, Validators.min(0)]],
      consultationFee: [0, [Validators.required, Validators.min(0)]],
      dueDate: ['', Validators.required],
    });

   
    this.transactionForm = this.fb.group({
      amount: [0, [Validators.required, Validators.min(0)]],
      paymentMethod: ['', Validators.required],
      description: [''],
      transactionType: ['PAYMENT', Validators.required]
    });
  }

  ngOnInit(): void {
   
    this.route.queryParams.subscribe((params) => {
      this.mode = params['mode'] || 'create';
      this.patientId = +params['patientId'] || null;

      
      if (this.mode === 'view' && this.patientId) {
        this.loadBillingDetails();
      }

      if (!this.patientId) {
        this.router.navigate(['/']);
      }
    });
  }


  createBill(): void {
    const billingDetails: BillingDetails = {
      ...this.billingForm.value,
      patient: { id: this.patientId } as any,
      totalAmount: this.calculateTotalAmount()
    };

    this.billingService.createBillWithTransactions(billingDetails).subscribe({
      next: (response) => {
        this.billingDetails = response;
        this.currentBillingId = response.id || null;
        this.transactions = response.transactions || [];
   
        this.remainingAmount = response.totalAmount || 0;
    
        this.transactionForm.patchValue({
          amount: this.remainingAmount
        });

        alert('Bill created successfully! You can now add a transaction.');
      },
      error: (error) => {
        console.error('Error creating bill:', error);
        alert('Failed to create the bill.');
      },
    });
  }

  calculateTotalAmount(): number {
    const { serviceCharge, medicationCharge, consultationFee } = this.billingForm.value;
    return (serviceCharge || 0) + (medicationCharge || 0) + (consultationFee || 0);
  }

  addTransaction(): void {
    if (!this.currentBillingId) {
      alert('Please create a bill first.');
      return;
    }

    const transactionData: Transaction = {
      ...this.transactionForm.value,
      transactionDate: new Date(),
      billingDetails: { id: this.currentBillingId } as any
    };

    this.billingService.addTransactionToBilling(this.currentBillingId, transactionData).subscribe({
      next: (updatedBillingDetails) => {
        this.billingDetails = updatedBillingDetails;
        this.transactions = updatedBillingDetails.transactions || [];
        

        this.updateRemainingAmount();

        this.transactionForm.reset({
          amount: this.remainingAmount,
          paymentMethod: '',
          description: '',
          transactionType: 'PAYMENT'
        });

        alert('Transaction added successfully!');
      },
      error: (error) => {
        console.error('Error adding transaction:', error);
        alert('Failed to add transaction.');
      }
    });
  }


  updateRemainingAmount(): void {
    if (this.billingDetails) {
      const totalTransactions = this.transactions.reduce((sum, transaction) => 
        sum + (transaction.amount || 0), 0);
      this.remainingAmount = Math.max((this.billingDetails.totalAmount || 0) - totalTransactions, 0);
    }
  }


  loadBillingDetails(): void {
    if (this.patientId) {
      this.billingService.getBillingDetailsById(this.patientId).subscribe({
        next: (details) => {
          this.billingDetails = details;
          this.currentBillingId = details.id || null;
          this.transactions = details.transactions || [];
 
          this.updateRemainingAmount();
        },
        error: (error) => {
          console.error('Error fetching billing details:', error);
          alert('Failed to fetch billing details.');
        },
      });
    }
  }

  calculateTotalPaid(): number {
    return this.transactions.reduce((sum, transaction) => 
      sum + (transaction.amount || 0), 0);
  }
}
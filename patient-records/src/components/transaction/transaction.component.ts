import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TransactionService } from '../../service/transaction.service'; 
import { Transaction } from '../../model/transaction.model';

@Component({
  selector: 'app-transaction',
  templateUrl: './transaction.component.html',
  styleUrls: ['./transaction.component.css']
})
export class TransactionComponent implements OnInit {
  transactionForm: FormGroup;
  transactions: Transaction[] = [];
  
  constructor(
    private fb: FormBuilder,
    private transactionService: TransactionService
  ) {
    this.transactionForm = this.fb.group({
      transactionDate: ['', Validators.required],
      transactionType: ['', Validators.required],
      amount: [0, [Validators.required, Validators.min(0)]],
      paymentMethod: ['', Validators.required],
      status: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadTransactions(); // Load existing transactions on component load
  }

  // Create a new transaction
  createTransaction(): void {
    if (this.transactionForm.valid) {
      const newTransaction: Transaction = this.transactionForm.value;
      
      // Use the TransactionService to send the new transaction to the backend
      this.transactionService.createTransaction(newTransaction).subscribe({
        next: (response) => {
          alert('Transaction created successfully!');
          this.loadTransactions(); // Reload transactions after successful creation
        },
        error: (error) => {
          console.error('Error creating transaction:', error);
          alert('Failed to create the transaction.');
        }
      });
    }
  }

  // Load all transactions
  loadTransactions(): void {
    this.transactionService.getAllTransactions().subscribe({
      next: (data) => {
        this.transactions = data;
      },
      error: (error) => {
        console.error('Error loading transactions:', error);
        alert('Failed to load transactions.');
      }
    });
  }
}

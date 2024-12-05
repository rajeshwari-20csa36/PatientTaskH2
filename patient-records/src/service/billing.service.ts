import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { BillingDetails } from '../model/BillingDetails.model';
import { Transaction } from '../model/transaction.model';


@Injectable({
  providedIn: 'root'
})
export class BillingService {
  getBillingDetails(patientId: number) {
      throw new Error('Method not implemented.');
  }
  private apiUrl = 'http://localhost:8080/api/v1/patients/billing';

  constructor(private http: HttpClient) {}

  createBillWithTransactions(billingDetails: BillingDetails): Observable<BillingDetails> {
    return this.http.post<BillingDetails>(this.apiUrl, billingDetails);
  }

  getAllBillingDetails(): Observable<BillingDetails[]> {
    return this.http.get<BillingDetails[]>(this.apiUrl);
  }

  getBillingDetailsById(id: number): Observable<BillingDetails> {
    return this.http.get<BillingDetails>(`${this.apiUrl}/${id}`);
  }

  getTransactionsForBilling(billingId: number): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/${billingId}/transactions`);
  }

  addTransactionToBilling(billingId: number, transaction: Transaction): Observable<BillingDetails> {
    return this.http.post<BillingDetails>(`${this.apiUrl}/${billingId}/transactions`, transaction);
  }
}
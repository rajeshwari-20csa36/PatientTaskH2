import { BillingDetails } from "./BillingDetails.model";
import { PaymentMethod } from "./enums/payment-method.enum";
import { TransactionStatus } from "./enums/transaction-status.enum";
import { TransactionType } from "./enums/transaction-type.enum";

export interface Transaction {
    id: number;
    transactionDate: Date;
    transactionType: TransactionType;
    amount: number;
    paymentMethod: PaymentMethod;
    description: string;
    status: TransactionStatus;
    billingDetails?: BillingDetails;
}
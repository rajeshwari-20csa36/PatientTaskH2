import { PaymentMethod } from "../enums/payment-method.enum";
import { TransactionType } from "../enums/transaction-type.enum";

export interface CreateTransactionDTO {
    amount: number;
    paymentMethod: PaymentMethod;
    description: string;
    transactionType: TransactionType;
}
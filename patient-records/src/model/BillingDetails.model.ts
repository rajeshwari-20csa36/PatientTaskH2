import { Patient } from "./patient.model";
import { Transaction } from "./transaction.model";

export interface BillingDetails{
   
    id: number;
    patient?: Patient;
    billDate: Date;
    serviceDescription: string;
    serviceCharge: number;
    medicationCharge: number;
    consultationFee: number;
    totalAmount: number;
    isFullyPaid: boolean;
    dueDate: Date;
    transactions?: Transaction[];
}
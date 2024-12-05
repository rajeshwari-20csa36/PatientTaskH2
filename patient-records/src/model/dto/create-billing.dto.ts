import { CreateTransactionDTO } from "./create-transaction.dto";

export interface CreateBillingDTO {
    patientId: number;
    serviceDescription: string;
    serviceCharge: number;
    medicationCharge: number;
    consultationFee: number;
    dueDate: Date;
    initialTransaction?: CreateTransactionDTO;
}
import { BillingDetails } from "./BillingDetails.model";

export interface Patient {
    id: number;
    name: string;
    age: number;
    gender: string;
    dateOfBirth: string;
    email: string;
    billingDetails?: BillingDetails[];
  }
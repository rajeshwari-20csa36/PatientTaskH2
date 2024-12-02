import { Routes } from '@angular/router';
import { PatientDetailsComponent } from '../components/patient-details/patient-details.component';
import { PatientFormComponent } from '../components/patient-form/patient-form.component';
import { PatientListComponent } from '../components/patient-list/patient-list.component';



    export const routes: Routes = [
        { path: '', redirectTo: '/patients', pathMatch: 'full' },
        { path: 'patients', component: PatientListComponent },
        { path: 'add-patient', component: PatientFormComponent },
        { path: 'patients/:id', component: PatientDetailsComponent }
      ];
      


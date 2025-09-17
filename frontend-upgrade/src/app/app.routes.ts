import { Routes } from '@angular/router';
import { HomePage } from './home-page/home-page';
import { TruckList } from './truck-list/truck-list';
import { TruckForm } from './truck-form/truck-form';

export const routes: Routes = [
  { path: '', component: HomePage, title: 'App Home Page' },
  { path: 'trucks', component: TruckList, title: 'Truck List' },
  { path: 'trucks/new', component: TruckForm, title: 'New Truck' },
  { path: 'trucks/edit/:id', component: TruckForm, title: 'Edit Truck' },
];

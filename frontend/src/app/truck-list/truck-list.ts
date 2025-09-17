import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterLink } from '@angular/router';
import { TruckService } from '../services/truck.service';

@Component({
  selector: 'app-truck-list',
  imports: [RouterLink, CommonModule],
  templateUrl: './truck-list.html',
})
export class TruckList {
  trucks: any[] = [];
  loading = true;
  error: string | null = null;

  constructor(private truckService: TruckService, private router: Router) {
    this.loadTrucks();
  }

  loadTrucks() {
    this.truckService.findAll().subscribe({
      next: (data) => {
        this.trucks = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = 'Erro ao carregar caminhões. O servidor backend pode estar indisponível.';
        this.loading = false;
      }
    });
  }

  editTruck(id: number) {
    this.router.navigate(['/trucks/edit', id]); // navegação programática
  }
}

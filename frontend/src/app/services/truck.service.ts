import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import { Truck } from '../models/truck.model';

@Injectable({
  providedIn: 'root',
})
export class TruckService {
  private readonly API_URL = 'http://localhost:8080/api/trucks';
  constructor(private http: HttpClient) { }

  private handleError(error: HttpErrorResponse) {
    console.error('Erro na requisição:', error);
    return throwError(() => error);
  }
  findAll(): Observable<Truck[]> {
    return this.http.get<Truck[]>(this.API_URL)
      .pipe(catchError(this.handleError));
  }

  findById(id: number): Observable<Truck> {
    return this.http.get<Truck>(`${this.API_URL}/${id}`)
      .pipe(catchError(this.handleError));
  }

  save(truck: Truck): Observable<Truck> {
    return this.http.post<Truck>(this.API_URL, truck)
      .pipe(catchError(this.handleError));
  }

  update(id: number, truck: Truck): Observable<Truck> {
    return this.http.put<Truck>(`${this.API_URL}/${id}`, truck)
      .pipe(catchError(this.handleError));
  }

}

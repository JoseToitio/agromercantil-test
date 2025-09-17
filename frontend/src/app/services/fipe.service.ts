import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class FipeService {
  private readonly API_URL = 'http://localhost:8080/api/fipe';

  constructor(private http: HttpClient) { }
  getBrands(): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/brands`);
  }

  getModels(brandCode: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/brands/${brandCode}/models`);
  }

  getYears(brandCode:string, modelCode: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.API_URL}/brands/${brandCode}/models/${modelCode}/years`);
  }

  getFipeInfo(brandCode: string, modelCode: string, yearCode: string): Observable<any> {
    const params = new HttpParams()
      .set('brand', brandCode)
      .set('model', modelCode)
      .set('year', yearCode);

    return this.http.get<any>(`${this.API_URL}/validate`, { params });
  }
}

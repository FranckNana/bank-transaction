import { Injectable } from '@angular/core';
import { environment } from '../../environnements/environnement';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Transaction } from '../models/Transaction';

@Injectable({
  providedIn: 'root'
})
export class SearchService {

  private  apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) { }

  getTransactionsById(id: string): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/transactions?id=${id}`);
  }

  getInvalidTransactions(): Observable<Transaction[]> {
    return this.http.get<Transaction[]>(`${this.apiUrl}/invalidtransact`);
  }
}

import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap, Subject } from 'rxjs';
import { PersonalExpense } from '../dto/personalexpense.interface';


@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private http = inject(HttpClient);

  getExpenses(category: string, begin: Date, end: Date): Observable<PersonalExpense[]> {
    var categoryAsString = category != null ? category : "";
    var beginAsString = begin != null ? begin.toString() + "" : "";
    var endAsString = end != null ? end.toString() + "" : "";
    var params = new HttpParams()
      .set('category', categoryAsString)
      .set('beginning', beginAsString)
      .set('end', endAsString);

    return this.http.get<PersonalExpense[]>('/personalexpenses/list', { params });
  }

  addExpense(expense: PersonalExpense): Observable<PersonalExpense> {
    let body = {
      'amount': expense.amount.toString(),
      'category': expense.category.toString(),
      'description': expense.description,
      'expensedate': expense.date.toISOString()
    };
    return this.http.post<PersonalExpense>('/personalexpenses/add', body);
  }

  getSum(category: string): Observable<number>{
    var params = new HttpParams().set('category', category);
    return this.http.get<number>('/personalexpenses/sum', { params }).pipe();
  }
}
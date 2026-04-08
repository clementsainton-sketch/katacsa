import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap, Subject } from 'rxjs';
import { PersonalExpense } from './personalexpense.interface';


@Injectable({
  providedIn: 'root',
})
export class ExpenseService {
  private http = inject(HttpClient);
  private expenses = signal<PersonalExpense[]>([])

  getExpenses(category: string, begin: Date, end: Date): Observable<PersonalExpense[]> {
    var params = new HttpParams();
    params.append('category', category);
    if(begin != null)
      params.append('beginning', begin.toLocaleDateString());
    if(end != null)
      params.append('end', end.toLocaleDateString());

    return this.http.get<PersonalExpense[]>('/personalexpenses/list', { params }).pipe(tap(expenses => this.expenses.set(expenses)));
  }

  addExpense(expense: PersonalExpense): void {
    const headers = {
      'amount': expense.amount.toString(),
      'category': expense.category.toString(),
      'description': expense.description,
      'expensedate': expense.date.toLocaleString()
    };
    this.http.post<PersonalExpense>('/personalexpenses/add', "", { headers }).subscribe();
  }

  private subject = new Subject<any>();


  getClickEvent(): Observable<any> {
    return this.subject.asObservable();
  }

  refreshList(): void {
    this.subject.next("");
  }
}
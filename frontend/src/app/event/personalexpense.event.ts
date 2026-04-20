import { Injectable, inject, signal } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable, tap, Subject } from 'rxjs';
import { PersonalExpense } from '../dto/personalexpense.interface';

@Injectable({
    providedIn: 'root',
})
export class ExpenseEvent {

  private subject = new Subject<any>();
  
    getClickEvent(): Observable<any> {
        return this.subject.asObservable();
    }

    refreshList(): void {
        this.subject.next("");
    }

}
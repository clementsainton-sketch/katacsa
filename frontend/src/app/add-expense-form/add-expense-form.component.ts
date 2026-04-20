import { Component, inject } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { NgFor } from '@angular/common';

import { ExpenseService } from '../service/personalexpense.service';
import { ExpenseEvent } from '../event/personalexpense.event';
import { PersonalExpense, Category } from '../dto/personalexpense.interface';
import { NgToastComponent, NgToastService } from 'ng-angular-popup';

@Component({
  selector: 'app-add-expense-form',
  imports: [ReactiveFormsModule, NgFor, NgToastComponent],
  templateUrl: './add-expense-form.component.html',
  styleUrl: './add-expense-form.component.css'
})

export class AddExpenseFormComponent {
  form: FormGroup = new FormGroup({
    amount: new FormControl(0),
    category: new FormControl(Category.FIXED),
    date: new FormControl(),
    description: new FormControl("")
  });

  private expenseService = inject(ExpenseService);
  private expenseEvent = inject(ExpenseEvent);
  public categories = Object.values(Category);
  constructor(private toast: NgToastService) { }

  onSubmit() {
    const expense: PersonalExpense = {
      amount: this.form.value.amount,
      category: this.form.value.category,
      date: new Date(this.form.value.date),
      description: this.form.value.description
    }

    this.toast.clearAll();
    if (this.form.value.amount == null) {
      this.toast.danger('You need to set an amount');
    } else if (this.form.value.date == null || this.form.value.date == "") {
      this.toast.danger('You need to set a date');
    } else {
      this.expenseService.addExpense(expense).subscribe({
        next: data => {
          this.expenseEvent.refreshList();
          this.toast.info('Expanse added');
        },
        error: exception => {
          let errorMessage = exception.error.message == undefined ? exception.error : exception.error.message;
          this.toast.danger(errorMessage);
        }
      });
    }
  }
}

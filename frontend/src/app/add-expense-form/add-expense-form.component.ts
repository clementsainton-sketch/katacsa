import { Component, inject } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { NgFor } from '@angular/common';
import { ExpenseService } from '../service/personalexpense.service';
import { PersonalExpense, Category } from '../service/personalexpense.interface';

@Component({
  selector: 'app-add-expense-form',
  imports: [ReactiveFormsModule, NgFor],
  templateUrl: './add-expense-form.component.html',
  styleUrl: './add-expense-form.component.css'
})

export class AddExpenseFormComponent {
  form: FormGroup = new FormGroup({
    amount: new FormControl(0),
    category: new FormControl(Category.FIXED),
    date: new FormControl(),
    description: new FormControl(""),
  });

  private expenseService = inject(ExpenseService);
  public categories = Object.values(Category);

  onSubmit() {
    const expense: PersonalExpense = {
      amount: this.form.value.amount,
      category: this.form.value.category,
      date: this.form.value.date,
      description: this.form.value.description
    }

    this.expenseService.addExpense(expense);
    this.expenseService.refreshList();
  }
}

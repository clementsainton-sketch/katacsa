import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AddExpenseFormComponent } from "./add-expense-form/add-expense-form.component";
import { ListExpensesTableComponent } from "./list-expenses-table/list-expenses-table.component";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, AddExpenseFormComponent, ListExpensesTableComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent { }

export enum Category { FIXED = "Fixed", FLEXIBLE = "Flexible", OCCASIONAL = "Occasional" }

export class PersonalExpense {
  amount!: number;
  date!: Date;
  description!: string;
  category!: Category;

  PersonalExpense(amount: number, date: Date, description: string, category: Category){
    this.amount = amount;
    this.date = date;
    this.description = description;
    this.category = category;
  }
}
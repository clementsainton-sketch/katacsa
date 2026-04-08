import { Component, inject, ViewChild } from '@angular/core';
import { ExpenseService } from '../service/personalexpense.service';
import { PersonalExpense, Category } from '../service/personalexpense.interface';
import { Subscription, Observable } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-list-expenses-table',
  imports: [MatTableModule, ReactiveFormsModule, NgFor],
  templateUrl: './list-expenses-table.component.html',
  styleUrl: './list-expenses-table.component.css'
})
export class ListExpensesTableComponent {
  private expenseService = inject(ExpenseService);
  categories = Object.values(Category).map(cat => cat + "").concat("----");
  private subscription!: Subscription;
  displayedColumns: string[] = ['amount', 'category', 'date', 'description'];

  dataSource = new MatTableDataSource<PersonalExpense>();
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;
  
  form: FormGroup = new FormGroup({
    category: new FormControl(Category.FIXED),
    begin: new FormControl(),
    end: new FormControl(),
  });

  clickEventsubscription = this.expenseService.getClickEvent().subscribe(() => {
    this.refresh();
  })

  ngOnInit() {
    this.subscription = this.refresh();
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  onSubmit() {

  }

  refresh() {
    return this.expenseService.getExpenses(this.form.value.category, this.form.value.begin, this.form.value.end).subscribe((expenses: PersonalExpense[]) => {
      console.log(expenses);
      this.dataSource.data = expenses;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }
}

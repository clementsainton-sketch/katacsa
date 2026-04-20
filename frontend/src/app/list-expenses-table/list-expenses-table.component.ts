import { Component, inject, ViewChild } from '@angular/core';
import { ExpenseService } from '../service/personalexpense.service';
import { ExpenseEvent } from '../event/personalexpense.event';
import { PersonalExpense, Category } from '../dto/personalexpense.interface';
import { Subscription } from 'rxjs';
import { MatTableModule } from '@angular/material/table';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { FormGroup, FormControl, ReactiveFormsModule } from "@angular/forms";
import { NgFor, DatePipe } from '@angular/common';
import { MatSortModule, Sort } from '@angular/material/sort';
import { LiveAnnouncer } from '@angular/cdk/a11y';

@Component({
  selector: 'app-list-expenses-table',
  imports: [MatTableModule, ReactiveFormsModule, NgFor, MatSortModule, DatePipe],
  templateUrl: './list-expenses-table.component.html',
  styleUrl: './list-expenses-table.component.css'
})
export class ListExpensesTableComponent {
  categorySum = ""
  private expenseService = inject(ExpenseService);
  private expenseEvent = inject(ExpenseEvent);
  categories = Object.values(Category).map(cat => cat + "").concat("");
  private subscription!: Subscription;
  displayedColumns: string[] = ['amount', 'category', 'date', 'description'];
  private _liveAnnouncer = inject(LiveAnnouncer);

  dataSource = new MatTableDataSource<PersonalExpense>();
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  @ViewChild(MatSort, { static: true }) sort!: MatSort;

  form: FormGroup = new FormGroup({
    category: new FormControl(),
    begin: new FormControl(),
    end: new FormControl(),
  });

  sortData(sortState: Sort) {
    if (sortState.direction) {
      this._liveAnnouncer.announce(`Sorted ${sortState.direction}ending`);
    } else {
      this._liveAnnouncer.announce('Sorting cleared');
    }
  }

  clickEventsubscription = this.expenseEvent.getClickEvent().subscribe(() => {
    this.refresh();
  })

  ngOnInit() {
    this.subscription = this.refresh();
  }

  ngOnDestroy() {
    this.subscription?.unsubscribe();
  }

  onSubmit() {
    this.categorySum = "";
    this.refresh();
    if (!this.isEmptyString(this.form.value.category) && this.isEmptyString(this.form.value.begin) && this.isEmptyString(this.form.value.end)) {
      this.sum(this.form.value.category);
    }
  }

  refresh() {
    return this.expenseService.getExpenses(this.form.value.category, this.form.value.begin, this.form.value.end).subscribe((expenses: PersonalExpense[]) => {
      this.dataSource.data = expenses;
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
      this.expenseEvent.refreshList();
    });
  }

  sum(category: string) {
    return this.expenseService.getSum(category).subscribe((sum: Number) => {
      this.categorySum = "Total of " + category + " expenses is " + sum;
    });
  }

  isEmptyString(string: string) {
    return string == null || string == "";
  }

  compare(a: number | string, b: number | string, isAsc: boolean) {
    return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
  }
}


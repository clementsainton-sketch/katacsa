import { Component, Input } from '@angular/core';
import { PersonalExpense } from '../app.component';

@Component({
  selector: 'app-list-expenses-table',
  imports: [],
  templateUrl: './list-expenses-table.component.html',
  styleUrl: './list-expenses-table.component.css'
})
export class ListExpensesTableComponent {
  @Input() data: PersonalExpense[] = []; // The table's data (array of type T)
  @Input() columns: { key: keyof PersonalExpense }[] = []; // Column definitions
}

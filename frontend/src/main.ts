import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { ApplicationRef } from '@angular/core';
import { createCustomElement } from '@angular/elements';

import { AppComponent } from './app/app.component';
import { AddExpenseFormComponent } from './app/add-expense-form/add-expense-form.component';
import { ListExpensesTableComponent } from './app/list-expenses-table/list-expenses-table.component';

(async () => {
  const app: ApplicationRef = await bootstrapApplication(AppComponent, appConfig);

  const addExpenseForm = createCustomElement(AddExpenseFormComponent, {
    injector: app.injector
  });
  const listExpensesTable = createCustomElement(ListExpensesTableComponent, {
    injector: app.injector
  });

  customElements.define('add-expense', addExpenseForm);
  customElements.define('list-expenses', listExpensesTable);
})();

import { Component } from '@angular/core';
import { FormGroup, FormControl, ReactiveFormsModule  } from "@angular/forms";
import { Category }  from '../app.component';
import { NgFor } from  '@angular/common';

@Component({
  selector: 'app-add-expense-form',
  imports: [ ReactiveFormsModule, NgFor ],
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

  public categories = Object.values(Category);

  
  


  onSubmit() {
      console.log(this.categories);
    // if (expense.name && expense.email) {
    //   // Envoi du formulaire
    //   console.log(this.user);
    // }
  }
}

export interface PersonalExpense {
  amount: number;
  date: Date;
  description: string;
  category: Category;
}

export enum Category { FIXED = "Fixed", FLEXIBLE = "Flexible", OCCASIONAL = "Occasional" }

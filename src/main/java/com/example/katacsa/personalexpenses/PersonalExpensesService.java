package com.example.katacsa.personalexpenses;

import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PersonalExpensesService {
    static final ArrayList<PersonalExpense> IN_MEMORY_EXPENSES = new ArrayList<>(); // Visible for tests

    public void addExpense(PersonalExpense personalExpense) {
        IN_MEMORY_EXPENSES.add(personalExpense);
    }

    public List<PersonalExpense> listExpenses(Category category, Date beginning, Date end) {
        List<PersonalExpense> expensesFiltered = new ArrayList<>(IN_MEMORY_EXPENSES);

        if (category != null) {
            expensesFiltered = expensesFiltered.stream().filter(expense -> category.equals(expense.category())).toList();
        }

        if (beginning != null) {
            expensesFiltered = expensesFiltered.stream().filter(expense -> expense.date().after(beginning)).toList();
        }

        if (end != null) {
            expensesFiltered = expensesFiltered.stream().filter(expense -> expense.date().before(end)).toList();
        }

        return expensesFiltered;
    }

    public double sumExpenses(Category category) {
        return IN_MEMORY_EXPENSES.stream().filter(expense -> category.equals(expense.category())).mapToDouble(PersonalExpense::amount).sum();
    }
}

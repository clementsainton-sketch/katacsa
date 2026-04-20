package com.example.katacsa.personalexpenses.repository;

import com.example.katacsa.personalexpenses.model.PersonalExpense;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class PersonalExpenseRepository {
    private static final ArrayList<PersonalExpense> IN_MEMORY_EXPENSES = new ArrayList<>();

    public void addExpense(PersonalExpense personalExpense){
        IN_MEMORY_EXPENSES.add(personalExpense);
    }

    public ArrayList<PersonalExpense> getExpenses(){
        return IN_MEMORY_EXPENSES;
    }

    public void clearExpenses(){
        IN_MEMORY_EXPENSES.clear();
    }
}

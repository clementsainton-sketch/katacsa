package com.example.katacsa.personalexpenses.service;

import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import com.example.katacsa.personalexpenses.exception.exceptions.IncoherentDateException;
import com.example.katacsa.personalexpenses.repository.PersonalExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonalExpensesService {
    @Autowired
    PersonalExpenseRepository repository;

    public PersonalExpensesService(PersonalExpenseRepository personalExpenseRepository) {
        repository = personalExpenseRepository;
    }

    public void addExpense(PersonalExpense personalExpense) {
        repository.addExpense(personalExpense);
    }

    public List<PersonalExpense> listExpenses(Optional<Category> category, Optional<Date> beginning, Optional<Date> end) {
        if (beginning.isPresent() && end.isPresent()) {
            if (beginning.get().after(end.get())) {
                throw new IncoherentDateException();
            }
        }

        List<PersonalExpense> expensesFiltered = new ArrayList<>(repository.getExpenses());

        if (category.isPresent()) {
            expensesFiltered = expensesFiltered.stream()
                    .filter(expense -> category.get().equals(expense.category())).toList();
        }

        if (beginning.isPresent()) {
            expensesFiltered = expensesFiltered.stream()
                    .filter(expense -> expense.date().equals(beginning.get())
                            || expense.date().after(beginning.get())).toList();
        }

        if (end.isPresent()) {
            expensesFiltered = expensesFiltered.stream()
                    .filter(expense -> expense.date().equals(end.get())
                            || expense.date().before(end.get())).toList();
        }

        return expensesFiltered;
    }

    public double sumExpenses(Category category) {
        return repository.getExpenses().stream().filter(expense -> category.equals(expense.category())).mapToDouble(PersonalExpense::amount).sum();
    }
}

package com.example.katacsa.personalexpenses.service;

import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import com.example.katacsa.personalexpenses.repository.PersonalExpenseRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

class PersonalExpensesServiceTest {

    @Mock
    private final PersonalExpenseRepository personalExpenseRepository = Mockito.mock(PersonalExpenseRepository.class);
    private final PersonalExpensesService personalExpensesService = new PersonalExpensesService(personalExpenseRepository);
    private Date testDate;
    private ArrayList<PersonalExpense> expenses;

    @BeforeEach
    void beforeEach() {
        expenses = new ArrayList<>();
        Mockito.doAnswer(call -> expenses.add(call.getArgument(0)))
                .when(personalExpenseRepository).addExpense(Mockito.any(PersonalExpense.class));
        Mockito.doReturn(expenses).when(personalExpenseRepository).getExpenses();
        testDate = Date.from(Instant.now());
    }

    @Test
    void addExpense() {
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.Fixed, testDate, "expense1"));
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.Flexible, testDate, "expense2"));
        Assertions.assertThat(expenses).hasSize(2);
    }

    @Test
    void listExpenses() {
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.Fixed, testDate, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.Flexible, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(Optional.empty(), Optional.empty(), Optional.empty())).contains(expense1, expense2);
    }

    @Test
    void listExpenses_byCategory() {
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.Fixed, testDate, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.Flexible, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(Optional.of(Category.Fixed), Optional.empty(), Optional.empty())).containsExactly(expense1);
    }

    @Test
    void listExpenses_byBegin() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.Fixed, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.Flexible, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(Optional.empty(), Optional.of(testDate), Optional.empty())).containsExactly(expense2);
    }

    @Test
    void listExpenses_byEnd() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.Fixed, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.Flexible, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(Optional.empty(), Optional.empty(), Optional.of(yesterday))).containsExactly(expense1);
    }

    @Test
    void listExpenses_byPeriod() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        Date tomorrow = Date.from(testDate.toInstant().plus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.Fixed, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.Flexible, testDate, "expense2");
        PersonalExpense expense3 = new PersonalExpense(10.01, Category.Flexible, tomorrow, "expense3");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        personalExpensesService.addExpense(expense3);
        Assertions.assertThat(personalExpensesService.listExpenses(Optional.empty(), Optional.of(testDate), Optional.of(testDate))).containsExactly(expense2);
    }

    @Test
    void sumExpenses() {
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.Fixed, testDate, "expense1"));
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.Flexible, testDate, "expense2"));
        Assertions.assertThat(expenses.stream().mapToDouble(PersonalExpense::amount).sum()).isEqualTo(20.02d);
    }
}
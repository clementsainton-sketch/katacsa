package com.example.katacsa.personalexpenses;

import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class PersonalExpensesServiceTest {

    private final PersonalExpensesService personalExpensesService = new PersonalExpensesService();
    private Date testDate;

    @BeforeEach
    void beforeEach() {
        PersonalExpensesService.IN_MEMORY_EXPENSES.clear();
        testDate = Date.from(Instant.now());
    }

    @Test
    void addExpense() {
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.FIXED, testDate, "expense1"));
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2"));
        Assertions.assertThat(PersonalExpensesService.IN_MEMORY_EXPENSES).hasSize(2);
    }

    @Test
    void listExpenses() {
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.FIXED, testDate, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(null, null, null)).contains(expense1, expense2);
    }

    @Test
    void listExpenses_byCategory() {
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.FIXED, testDate, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(Category.FIXED, null, null)).containsExactly(expense1);
    }

    @Test
    void listExpenses_byBegin() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.FIXED, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(null, yesterday, null)).containsExactly(expense2);
    }

    @Test
    void listExpenses_byEnd() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.FIXED, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        Assertions.assertThat(personalExpensesService.listExpenses(null, null, testDate)).containsExactly(expense1);
    }

    @Test
    void listExpenses_byPeriod() {
        Date yesterday = Date.from(testDate.toInstant().minus(1, ChronoUnit.DAYS));
        Date tomorrow = Date.from(testDate.toInstant().plus(1, ChronoUnit.DAYS));
        PersonalExpense expense1 = new PersonalExpense(10.01, Category.FIXED, yesterday, "expense1");
        PersonalExpense expense2 = new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2");
        PersonalExpense expense3 = new PersonalExpense(10.01, Category.FLEXIBLE, tomorrow, "expense3");
        personalExpensesService.addExpense(expense1);
        personalExpensesService.addExpense(expense2);
        personalExpensesService.addExpense(expense3);
        Assertions.assertThat(personalExpensesService.listExpenses(null, yesterday, tomorrow)).containsExactly(expense2);
    }

    @Test
    void sumExpenses() {
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.FIXED, testDate, "expense1"));
        personalExpensesService.addExpense(new PersonalExpense(10.01, Category.FLEXIBLE, testDate, "expense2"));
        Assertions.assertThat(PersonalExpensesService.IN_MEMORY_EXPENSES.stream().mapToDouble(PersonalExpense::amount).sum()).isEqualTo(20.02d);
    }
}
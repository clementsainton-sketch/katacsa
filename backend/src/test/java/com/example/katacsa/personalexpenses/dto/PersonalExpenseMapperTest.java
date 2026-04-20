package com.example.katacsa.personalexpenses.dto;

import com.example.katacsa.personalexpenses.exception.exceptions.FutureDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.WrongAmountException;
import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

class PersonalExpenseMapperTest {
    private final PersonalExpenseMapper mapper = new PersonalExpenseMapper();

    @Test
    void toPersonalExpense() {
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO(10, Category.Fixed, new Date(), "description");
        Assertions.assertThat(mapper.toPersonalExpense(personalExpenseDTO)).isInstanceOf(PersonalExpense.class);
    }

    @Test
    void toPersonalExpense_wrongAmount() {
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO(-10, Category.Fixed, new Date(), "description");
        Assertions.assertThatThrownBy(() -> mapper.toPersonalExpense(personalExpenseDTO))
                .isInstanceOf(WrongAmountException.class);
    }

    @Test
    void toPersonalExpense_futureHour() {
        Date dateInOneHour = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO(10, Category.Fixed, dateInOneHour, "description");
        Assertions.assertThatThrownBy(() -> mapper.toPersonalExpense(personalExpenseDTO))
                .isInstanceOf(FutureDateException.class);
    }
}
package com.example.katacsa.personalexpenses.dto;

import com.example.katacsa.personalexpenses.exception.exceptions.FutureDateException;
import com.example.katacsa.personalexpenses.exception.exceptions.WrongAmountException;
import com.example.katacsa.personalexpenses.model.PersonalExpense;

import java.time.Instant;
import java.util.Date;

public class PersonalExpenseMapper {
    public PersonalExpense toPersonalExpense(PersonalExpenseDTO personalExpenseDTO) {
        if(personalExpenseDTO.amount() < 0){
            throw new WrongAmountException();
        }
        if(personalExpenseDTO.expensedate().after(Date.from(Instant.now()))){
            throw new FutureDateException();
        }
        return new PersonalExpense(personalExpenseDTO.amount(),
                personalExpenseDTO.category(),
                personalExpenseDTO.expensedate(),
                personalExpenseDTO.description());
    }
}

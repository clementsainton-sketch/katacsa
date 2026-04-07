package com.example.katacsa.personalexpenses.model;

import java.util.Date;

public record PersonalExpense(double amount,
                              Category category,
                              Date date,
                              String description) {
}

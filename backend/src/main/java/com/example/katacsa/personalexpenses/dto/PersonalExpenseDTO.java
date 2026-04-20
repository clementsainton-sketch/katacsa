package com.example.katacsa.personalexpenses.dto;

import com.example.katacsa.personalexpenses.model.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public record PersonalExpenseDTO(@Positive double amount,
                                 @Valid Category category,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) @NotNull Date expensedate,
                                 String description) {
}

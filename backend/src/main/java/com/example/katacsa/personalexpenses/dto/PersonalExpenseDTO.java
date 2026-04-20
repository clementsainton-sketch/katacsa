package com.example.katacsa.personalexpenses.dto;

import com.example.katacsa.personalexpenses.model.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Date;

public record PersonalExpenseDTO(@Positive double amount,
                                 @Valid Category category,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) @NotNull Date expenseDate,
                                 String description) {
}

package com.example.katacsa.personalexpenses.controller;

import com.example.katacsa.personalexpenses.dto.PersonalExpenseDTO;
import com.example.katacsa.personalexpenses.dto.PersonalExpenseMapper;
import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import com.example.katacsa.personalexpenses.service.PersonalExpensesService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/personalexpenses")
public class PersonalExpensesController {
    @Autowired
    private PersonalExpensesService personalExpensesService;
    private final PersonalExpenseMapper personalExpenseMapper = new PersonalExpenseMapper();
    final Gson gson = new GsonBuilder().create();

    @PostMapping(value = "/add")
    @ResponseStatus
    public ResponseEntity<?> addExpense(@RequestBody @Valid PersonalExpenseDTO personalExpenseDTO) {
        PersonalExpense personalExpense;
        personalExpense = personalExpenseMapper.toPersonalExpense(personalExpenseDTO);
        personalExpensesService.addExpense(personalExpense);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> listExpenses(@RequestParam Optional<Category> category,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> beginning,
                                          @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> end) {

        return ResponseEntity.ok().body(gson.toJson(personalExpensesService.listExpenses(category, beginning, end)));
    }

    @GetMapping(value = "/sum", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sumExpenses(@RequestParam Category category) {
        return ResponseEntity.ok().body(gson.toJson((personalExpensesService.sumExpenses(category))));
    }
}

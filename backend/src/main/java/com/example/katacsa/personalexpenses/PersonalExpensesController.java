package com.example.katacsa.personalexpenses;

import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/personalexpenses")
public class PersonalExpensesController {
    @Autowired
    private PersonalExpensesService personalExpensesService;

    @PostMapping("/add")
    @ResponseStatus
    public ResponseEntity<?> addExpense(@RequestHeader double amount,
                                        @RequestHeader Category category,
                                        @RequestHeader @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expenseDate,
                                        @RequestHeader String description) {
        personalExpensesService.addExpense(new PersonalExpense(amount, category, expenseDate, description));

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/list")
    public List<PersonalExpense> listExpenses(@RequestParam Optional<Category> category,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> beginning,
                                              @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<Date> end) {
        if (beginning.isPresent() && end.isPresent()) {
            if (beginning.get().after(end.get())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incoherent dates");
            }
        }

        return personalExpensesService.listExpenses(category.orElse(null), beginning.orElse(null), end.orElse(null));
    }

    @GetMapping("/sum")
    public String sumExpenses(@RequestParam Category category) {
        return String.valueOf(personalExpensesService.sumExpenses(category));
    }
}

package com.example.katacsa.personalexpenses.controller;

import com.example.katacsa.personalexpenses.dto.PersonalExpenseDTO;
import com.example.katacsa.personalexpenses.model.Category;
import com.example.katacsa.personalexpenses.model.PersonalExpense;
import com.example.katacsa.personalexpenses.service.PersonalExpensesService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.client.RestTestClient;

import java.util.Date;


@WebMvcTest(PersonalExpensesController.class)
@AutoConfigureRestTestClient
class PersonalExpensesControllerTest {

    @Autowired
    private RestTestClient restTestClient;

    @MockitoBean
    private PersonalExpensesService service;

    @Test
    void addExpense() {
        PersonalExpenseDTO personalExpenseDTO = new PersonalExpenseDTO(10, Category.Occasional, new Date(), "getting mondey");
        restTestClient.post()
                .uri("/personalexpenses/add")
                .body(personalExpenseDTO)
                .exchange()
                .expectStatus().isCreated();

        personalExpenseDTO = new PersonalExpenseDTO(-10, Category.Occasional, new Date(), "getting mondey");
        restTestClient.post()
                .uri("/personalexpenses/add")
                .body(personalExpenseDTO)
                .exchange()
                .expectStatus().isBadRequest();

        Mockito.verify(service, Mockito.times(1)).addExpense(ArgumentMatchers.any(PersonalExpense.class));
    }

    @Test
    void listExpenses() {
        restTestClient.get()
                .uri("/personalexpenses/list")
                .exchange()
                .expectStatus().isOk();

        restTestClient.get()
                .uri("/personalexpenses/list")
                .header("category", "FIXED")
                .header("beginning", "2026-04-05")
                .header("end", "2026-04-06")
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void sumExpenses() {
        restTestClient.get()
                .uri("/personalexpenses/sum?category=Fixed")
                .exchange()
                .expectStatus().isOk();

        restTestClient.get()
                .uri("/personalexpenses/sum?category=FIXEDDD")
                .exchange()
                .expectStatus().isBadRequest();
    }
}